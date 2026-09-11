package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ZakaAmber
import com.example.ui.theme.ZakaRose
import com.example.ui.theme.ZakaTeal
import com.example.ui.viewmodel.ZakaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: ZakaViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions by viewModel.currentQuizQuestions.collectAsState()
    val currentIndex by viewModel.currentQuizIndex.collectAsState()
    val selectedOption by viewModel.selectedOption.collectAsState()
    val isSubmitted by viewModel.isAnswerSubmitted.collectAsState()
    val score by viewModel.quizScore.collectAsState()
    val isFinished by viewModel.isQuizFinished.collectAsState()

    val currentQ = questions.getOrNull(currentIndex)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chapter MCQ Test", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Chapter Selector Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                item {
                    FilterChip(
                        selected = true,
                        onClick = { viewModel.startCumulativeQuiz() },
                        label = { Text("All Chapters (Mock)") },
                        leadingIcon = { Icon(Icons.Default.Shuffle, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
                items((1..8).toList()) { chNum ->
                    FilterChip(
                        selected = false,
                        onClick = { viewModel.startChapterQuiz(chNum) },
                        label = { Text("Ch $chNum") }
                    )
                }
            }

            if (isFinished) {
                // Quiz Completed Summary Screen
                QuizSummaryView(
                    score = score,
                    total = questions.size,
                    onRetry = { viewModel.startCumulativeQuiz() },
                    onDone = onNavigateBack
                )
            } else if (currentQ != null) {
                // Progress Bar
                val progress = (currentIndex + 1).toFloat() / questions.size
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Question ${currentIndex + 1} of ${questions.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Score: $score",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Question Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quiz_question_card"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Text(
                            text = currentQ.question,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.padding(18.dp),
                            lineHeight = 24.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Options A, B, C, D
                    currentQ.options.forEachIndexed { optIndex, optionText ->
                        val isChosen = selectedOption == optIndex
                        val isCorrect = optIndex == currentQ.correctOptionIndex

                        val (cardColor, borderColor, textColor) = when {
                            !isSubmitted && isChosen -> Triple(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            isSubmitted && isCorrect -> Triple(
                                ZakaTeal.copy(alpha = 0.2f),
                                ZakaTeal,
                                ZakaTeal
                            )
                            isSubmitted && isChosen && !isCorrect -> Triple(
                                ZakaRose.copy(alpha = 0.2f),
                                ZakaRose,
                                ZakaRose
                            )
                            else -> Triple(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.colorScheme.outlineVariant,
                                MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !isSubmitted) { viewModel.selectQuizOption(optIndex) }
                                .testTag("quiz_option_$optIndex"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = cardColor),
                            border = BorderStroke(1.5.dp, borderColor)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = if (isChosen || (isSubmitted && isCorrect)) borderColor else MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        val optLetter = when (optIndex) {
                                            0 -> "A"
                                            1 -> "B"
                                            2 -> "C"
                                            else -> "D"
                                        }
                                        Text(
                                            text = optLetter,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isChosen || (isSubmitted && isCorrect)) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = optionText,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                    color = if (isSubmitted && (isCorrect || isChosen)) textColor else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.weight(1f)
                                )

                                if (isSubmitted) {
                                    if (isCorrect) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = ZakaTeal)
                                    } else if (isChosen) {
                                        Icon(Icons.Default.Cancel, contentDescription = "Incorrect", tint = ZakaRose)
                                    }
                                }
                            }
                        }
                    }

                    // Immediate Feedback Explanation Box
                    if (isSubmitted) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("quiz_explanation_card"),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedOption == currentQ.correctOptionIndex)
                                    ZakaTeal.copy(alpha = 0.12f)
                                else
                                    ZakaRose.copy(alpha = 0.12f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (selectedOption == currentQ.correctOptionIndex) Icons.Default.Check else Icons.Default.Info,
                                        contentDescription = null,
                                        tint = if (selectedOption == currentQ.correctOptionIndex) ZakaTeal else ZakaRose,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (selectedOption == currentQ.correctOptionIndex) "Correct! Explanation:" else "Incorrect. Explanation:",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = if (selectedOption == currentQ.correctOptionIndex) ZakaTeal else ZakaRose
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = currentQ.explanation,
                                    style = MaterialTheme.typography.bodySmall,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }

                // Action Button (Submit or Next)
                Spacer(modifier = Modifier.height(12.dp))
                if (!isSubmitted) {
                    Button(
                        onClick = { viewModel.submitQuizAnswer() },
                        enabled = selectedOption != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("btn_submit_quiz_answer"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Check Answer", style = MaterialTheme.typography.titleSmall)
                    }
                } else {
                    Button(
                        onClick = { viewModel.nextQuizQuestion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("btn_next_quiz_question"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = if (currentIndex + 1 < questions.size) "Next Question" else "Finish & View Results",
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = { viewModel.startCumulativeQuiz() }) {
                        Text("Start Quiz")
                    }
                }
            }
        }
    }
}

@Composable
fun QuizSummaryView(
    score: Int,
    total: Int,
    onRetry: () -> Unit,
    onDone: () -> Unit
) {
    val percentage = if (total > 0) (score.toFloat() / total) * 100 else 0f
    val (grade, remark, gradeColor) = when {
        percentage >= 90 -> Triple("A-1", "Outstanding Mastery! Ready for Board Exams!", ZakaTeal)
        percentage >= 80 -> Triple("A", "Excellent performance! Keep it up.", ZakaTeal)
        percentage >= 70 -> Triple("B", "Good effort. Review weaker topics with AI Tutor.", ZakaAmber)
        else -> Triple("C", "Review the chapter textbook notes and re-attempt.", ZakaRose)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    color = gradeColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = grade,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = gradeColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Quiz Completed!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You scored $score out of $total questions (${percentage.toInt()}%)",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = remark,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onRetry,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Try Again")
                    }

                    Button(
                        onClick = onDone,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    ) {
                        Text("Done")
                    }
                }
            }
        }
    }
}
