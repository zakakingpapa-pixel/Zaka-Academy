package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.course.CourseRepository
import com.example.model.AnswerVerdict
import com.example.model.PracticeQuestion
import com.example.model.QuestionDifficulty
import com.example.model.QuestionType
import com.example.ui.theme.ZakaAmber
import com.example.ui.theme.ZakaRose
import com.example.ui.theme.ZakaTeal
import com.example.ui.viewmodel.ZakaViewModel

/**
 * ZAKA Question Tester: pick a chapter, question type, difficulty and length, then answer in your
 * own words and get concept-based feedback with a hint ladder.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTesterScreen(
    viewModel: ZakaViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions by viewModel.testerQuestions.collectAsState()
    val index by viewModel.testerIndex.collectAsState()
    val answer by viewModel.testerAnswer.collectAsState()
    val evaluation by viewModel.testerEvaluation.collectAsState()
    val hints by viewModel.testerHints.collectAsState()
    val showFullAnswer by viewModel.testerShowFullAnswer.collectAsState()
    val isEvaluating by viewModel.isEvaluating.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "ZAKA Question Tester",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
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
        Box(modifier = Modifier.padding(innerPadding)) {
            val current = questions.getOrNull(index)
            if (current == null) {
                TesterSetup(
                    onStart = { chapterId, types, difficulties, count ->
                        viewModel.startQuestionTester(chapterId, types, difficulties, count)
                    }
                )
            } else {
                TesterRunner(
                    question = current,
                    position = index + 1,
                    total = questions.size,
                    answer = answer,
                    hints = hints,
                    showFullAnswer = showFullAnswer,
                    isEvaluating = isEvaluating,
                    evaluation = evaluation,
                    onAnswerChange = viewModel::updateTesterAnswer,
                    onCheck = viewModel::checkTesterAnswer,
                    onHint = viewModel::revealNextHint,
                    onShowAnswer = viewModel::revealFullAnswer,
                    onNext = viewModel::nextTesterQuestion,
                    isLast = index == questions.lastIndex
                )
            }
        }
    }
}

@Composable
private fun TesterSetup(
    onStart: (Int?, List<QuestionType>, List<QuestionDifficulty>, Int) -> Unit
) {
    var chapterId by remember { mutableStateOf<Int?>(null) }
    var selectedTypes by remember {
        mutableStateOf(setOf(QuestionType.SHORT, QuestionType.LONG, QuestionType.DEFINITION, QuestionType.CONCEPTUAL))
    }
    var selectedDifficulties by remember { mutableStateOf(emptySet<QuestionDifficulty>()) }
    var count by remember { mutableIntStateOf(5) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Written practice with concept-based checking. Your answer is graded on ideas and key points, never on exact wording.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        SectionLabel("Chapter")
        FlowChips {
            FilterChip(
                selected = chapterId == null,
                onClick = { chapterId = null },
                label = { Text("All chapters") }
            )
            CourseRepository.allChapters.forEach { chapter ->
                FilterChip(
                    selected = chapterId == chapter.id,
                    onClick = { chapterId = chapter.id },
                    label = { Text("Ch ${chapter.number}") },
                    modifier = Modifier.testTag("tester_chapter_${chapter.id}")
                )
            }
        }

        SectionLabel("Question type")
        FlowChips {
            listOf(
                QuestionType.SHORT,
                QuestionType.LONG,
                QuestionType.DEFINITION,
                QuestionType.CONCEPTUAL
            ).forEach { type ->
                FilterChip(
                    selected = type in selectedTypes,
                    onClick = {
                        selectedTypes = if (type in selectedTypes) selectedTypes - type else selectedTypes + type
                    },
                    label = { Text(type.label) }
                )
            }
        }

        SectionLabel("Difficulty")
        FlowChips {
            QuestionDifficulty.entries.forEach { difficulty ->
                FilterChip(
                    selected = difficulty in selectedDifficulties,
                    onClick = {
                        selectedDifficulties = if (difficulty in selectedDifficulties) {
                            selectedDifficulties - difficulty
                        } else {
                            selectedDifficulties + difficulty
                        }
                    },
                    label = { Text(difficulty.label) }
                )
            }
        }

        SectionLabel("Number of questions")
        FlowChips {
            listOf(3, 5, 10, 15).forEach { option ->
                FilterChip(
                    selected = count == option,
                    onClick = { count = option },
                    label = { Text("$option") }
                )
            }
        }

        Button(
            onClick = {
                onStart(
                    chapterId,
                    selectedTypes.toList().ifEmpty {
                        listOf(QuestionType.SHORT, QuestionType.LONG, QuestionType.DEFINITION, QuestionType.CONCEPTUAL)
                    },
                    selectedDifficulties.toList(),
                    count
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_start_tester"),
            shape = RoundedCornerShape(14.dp)
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Start Testing")
        }
    }
}

@Composable
private fun TesterRunner(
    question: PracticeQuestion,
    position: Int,
    total: Int,
    answer: String,
    hints: List<String>,
    showFullAnswer: Boolean,
    isEvaluating: Boolean,
    evaluation: com.example.model.AnswerEvaluation?,
    onAnswerChange: (String) -> Unit,
    onCheck: () -> Unit,
    onHint: () -> Unit,
    onShowAnswer: () -> Unit,
    onNext: () -> Unit,
    isLast: Boolean
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
            "Question $position of $total · ${question.type.label} · ${question.difficulty.label}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    question.question,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "${question.chapterTitle} · ${question.topicTitle}\nSource: ${question.source.label}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        OutlinedTextField(
            value = answer,
            onValueChange = onAnswerChange,
            label = { Text("Write your answer") },
            minLines = 5,
            enabled = evaluation == null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("tester_answer_field")
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = onHint,
                enabled = hints.size < 3,
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Lightbulb, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text(if (hints.isEmpty()) "Hint" else "Hint ${hints.size + 1}")
            }
            OutlinedButton(
                onClick = onShowAnswer,
                enabled = !showFullAnswer,
                modifier = Modifier.weight(1f)
            ) {
                Text("Full Answer")
            }
        }

        hints.forEachIndexed { i, hint ->
            Card(
                colors = CardDefaults.cardColors(containerColor = ZakaAmber.copy(alpha = 0.12f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Hint ${i + 1}: $hint",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (showFullAnswer) {
            Card(shape = RoundedCornerShape(12.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text("Model Answer", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    Spacer(Modifier.height(4.dp))
                    Text(question.modelAnswer, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Button(
            onClick = onCheck,
            enabled = !isEvaluating && evaluation == null && answer.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_check_answer"),
            shape = RoundedCornerShape(14.dp)
        ) {
            if (isEvaluating) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                Spacer(Modifier.width(8.dp))
                Text("Checking…")
            } else {
                Text("Check Answer")
            }
        }

        evaluation?.let { EvaluationCard(it) }

        if (evaluation != null && !isLast) {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_next_question"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Next Question")
            }
        } else if (evaluation != null) {
            Text(
                "That was the last question in this set.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EvaluationCard(evaluation: com.example.model.AnswerEvaluation) {
    val accent = when (evaluation.verdict) {
        AnswerVerdict.CORRECT -> ZakaTeal
        AnswerVerdict.PARTIALLY_CORRECT -> ZakaAmber
        else -> ZakaRose
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = accent.copy(alpha = 0.12f)),
        modifier = Modifier.testTag("evaluation_card")
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${evaluation.verdict.symbol} ${evaluation.verdict.label}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = accent
                )
                Spacer(Modifier.weight(1f))
                Text(
                    "${evaluation.scorePercent}%",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Text(evaluation.feedback, style = MaterialTheme.typography.bodyMedium)

            if (evaluation.coveredPoints.isNotEmpty()) {
                Text("What you got right", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                evaluation.coveredPoints.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }
            }

            if (evaluation.missingPoints.isNotEmpty()) {
                Text("What is missing", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                evaluation.missingPoints.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }
            }

            Text("Board-ready answer", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
            Text(evaluation.modelAnswer, style = MaterialTheme.typography.bodyMedium)

            HorizontalDivider()
            Text(
                "${evaluation.disclaimer}\nChecked by: ${evaluation.evaluatedBy}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
internal fun SectionLabel(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun FlowChips(content: @Composable () -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) { content() }
}
