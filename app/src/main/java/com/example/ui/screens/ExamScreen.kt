package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.course.CourseRepository
import com.example.feature.exam.ExamEngine
import com.example.model.ExamConfig
import com.example.model.ExamMode
import com.example.model.ExamSummary
import com.example.model.PracticeQuestion
import com.example.model.QuestionType
import com.example.ui.viewmodel.ZakaViewModel

/**
 * Chapter tests and the full mock exam. Practice mode reveals feedback per question; exam mode
 * holds all feedback back until the paper is finished.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    viewModel: ZakaViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val config by viewModel.examConfig.collectAsState()
    val questions by viewModel.examQuestions.collectAsState()
    val index by viewModel.examIndex.collectAsState()
    val draftAnswer by viewModel.examDraftAnswer.collectAsState()
    val draftOption by viewModel.examDraftOption.collectAsState()
    val answers by viewModel.examAnswers.collectAsState()
    val summary by viewModel.examSummary.collectAsState()
    val isEvaluating by viewModel.isEvaluating.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        config?.title ?: "Tests & Mock Exam",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            val activeConfig = config
            val currentQuestion = questions.getOrNull(index)
            when {
                summary != null -> ExamSummaryView(summary!!) { viewModel.startExam(it) }
                activeConfig != null && currentQuestion != null -> ExamRunner(
                    config = activeConfig,
                    question = currentQuestion,
                    position = index + 1,
                    total = questions.size,
                    draftAnswer = draftAnswer,
                    draftOption = draftOption,
                    lastEvaluation = answers.lastOrNull()?.evaluation,
                    isEvaluating = isEvaluating,
                    onAnswerChange = viewModel::updateExamDraftAnswer,
                    onSelectOption = viewModel::selectExamOption,
                    onSubmit = viewModel::submitExamAnswer
                )
                else -> ExamSetup { viewModel.startExam(it) }
            }
        }
    }
}

@Composable
private fun ExamSetup(onStart: (ExamConfig) -> Unit) {
    var mode by remember { mutableStateOf(ExamMode.PRACTICE) }
    var chapterId by remember { mutableStateOf<Int?>(null) }
    var count by remember { mutableIntStateOf(15) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionLabel("Mode")
        ExamMode.entries.forEach { option ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (mode == option) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(selected = mode == option, onClick = { mode = option })
            ) {
                Column(Modifier.padding(12.dp)) {
                    Text(option.label, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    Text(
                        option.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        SectionLabel("Chapter")
        FlowChips {
            FilterChip(
                selected = chapterId == null,
                onClick = { chapterId = null },
                label = { Text("Full course") }
            )
            CourseRepository.allChapters.forEach { chapter ->
                FilterChip(
                    selected = chapterId == chapter.id,
                    onClick = { chapterId = chapter.id },
                    label = { Text("Ch ${chapter.number}") }
                )
            }
        }

        SectionLabel("Questions")
        FlowChips {
            listOf(5, 10, 15, 30).forEach { option ->
                FilterChip(
                    selected = count == option,
                    onClick = { count = option },
                    label = { Text("$option") }
                )
            }
        }

        Button(
            onClick = {
                val chapters = chapterId?.let { listOf(it) } ?: CourseRepository.allChapters.map { it.id }
                val title = when {
                    mode == ExamMode.EXAM && chapterId == null -> "Full Mock Exam (All Chapters)"
                    chapterId != null -> "${mode.label}: ${CourseRepository.getChapter(chapterId!!)?.title ?: "Chapter"}"
                    else -> "${mode.label}: Full Course"
                }
                onStart(
                    ExamConfig(
                        mode = mode,
                        title = title,
                        chapterIds = chapters,
                        types = QuestionType.entries.toList(),
                        questionCount = count
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_start_exam"),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Start")
        }

        Text(
            "Written answers are graded by the same concept-based checker used in the Question Tester: practice feedback, not an official board score.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ExamRunner(
    config: ExamConfig,
    question: PracticeQuestion,
    position: Int,
    total: Int,
    draftAnswer: String,
    draftOption: Int?,
    lastEvaluation: com.example.model.AnswerEvaluation?,
    isEvaluating: Boolean,
    onAnswerChange: (String) -> Unit,
    onSelectOption: (Int) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        LinearProgressIndicator(
            progress = { position.toFloat() / total.coerceAtLeast(1) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Question $position of $total · ${question.type.label} · ${question.chapterTitle}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(shape = RoundedCornerShape(16.dp)) {
            Text(
                question.question,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        if (question.type == QuestionType.MCQ) {
            question.options.forEachIndexed { i, option ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (draftOption == i) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(selected = draftOption == i, onClick = { onSelectOption(i) })
                        .testTag("exam_option_$i")
                ) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = draftOption == i, onClick = { onSelectOption(i) })
                        Spacer(Modifier.width(8.dp))
                        Text(option, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        } else {
            OutlinedTextField(
                value = draftAnswer,
                onValueChange = onAnswerChange,
                label = { Text("Write your answer") },
                minLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("exam_answer_field")
            )
        }

        Button(
            onClick = onSubmit,
            enabled = !isEvaluating &&
                (question.type != QuestionType.MCQ && draftAnswer.isNotBlank() || draftOption != null),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_submit_exam_answer"),
            shape = RoundedCornerShape(14.dp)
        ) {
            if (isEvaluating) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                Spacer(Modifier.width(8.dp))
                Text("Checking…")
            } else {
                Text(if (position == total) "Finish Paper" else "Submit & Next")
            }
        }

        if (config.mode == ExamMode.PRACTICE && lastEvaluation != null) {
            Text("Feedback on your previous answer", style = MaterialTheme.typography.titleSmall)
            EvaluationCard(lastEvaluation)
        }
    }
}

@Composable
private fun ExamSummaryView(summary: ExamSummary, onRestart: (ExamConfig) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text(summary.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                Text(
                    "${summary.scorePercent}% · ${summary.totalQuestions} questions",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    "✅ ${summary.correct} correct · 🟡 ${summary.partial} partial · ❌ ${summary.incorrect} incorrect",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        SectionLabel("Chapter breakdown")
        summary.chapterBreakdown.forEach { performance ->
            Card(shape = RoundedCornerShape(12.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text(performance.chapterTitle, style = MaterialTheme.typography.titleSmall)
                    Text(
                        "${performance.scorePercent}% over ${performance.answered} question(s)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        SectionLabel("What to do next")
        summary.recommendations.forEach { Text("• $it", style = MaterialTheme.typography.bodyMedium) }

        Button(
            onClick = { onRestart(ExamEngine.fullMockExam()) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Start a Full Mock Exam")
        }
    }
}
