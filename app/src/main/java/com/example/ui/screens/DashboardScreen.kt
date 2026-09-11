package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChapterItem
import com.example.model.PracticeQuestion
import com.example.ui.theme.ZakaAmber
import com.example.ui.theme.ZakaIndigo
import com.example.ui.theme.ZakaTeal
import com.example.ui.viewmodel.ZakaViewModel

@Composable
fun DashboardScreen(
    viewModel: ZakaViewModel,
    onNavigateToCourse: () -> Unit,
    onNavigateToTopic: (String) -> Unit,
    onNavigateToQuiz: (Int) -> Unit,
    onNavigateToFlashcards: (Int) -> Unit,
    onNavigateToAiTeacher: (String?) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToTester: () -> Unit,
    onNavigateToExam: () -> Unit,
    onNavigateToProgress: () -> Unit,
    onNavigateToNotes: () -> Unit,
    onNavigateToErrorSolver: () -> Unit,
    onStartDailyChallenge: (PracticeQuestion) -> Unit,
    modifier: Modifier = Modifier
) {
    val topicProgress by viewModel.topicProgress.collectAsState()
    val userStreak by viewModel.userStreak.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val snapshot by viewModel.learningSnapshot.collectAsState()
    val dailyChallenges by viewModel.dailyChallenges.collectAsState()

    val completedCount = topicProgress.count { it.isCompleted }
    val averageLabel = snapshot.overallScore?.let { "$it%" } ?: "—"
    val dailyQuestion = remember { viewModel.dailyChallengeQuestion() }
    val todayKey = viewModel.todayKey()
    val dailyDone = dailyChallenges.any { it.date == todayKey && it.isCompleted }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
    ) {
        // Hero Header
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_hero_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f)
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.White.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.School,
                                        contentDescription = "Zaka Academy",
                                        tint = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "ZAKA ACADEMY",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp
                                        ),
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Class 9 Computer Science",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.White.copy(alpha = 0.85f)
                                    )
                                }
                            }

                            IconButton(
                                onClick = onNavigateToSettings,
                                modifier = Modifier.testTag("settings_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings",
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Welcome to your complete CS study platform. Master every chapter with textbook notes, immediate quizzes, and our intelligent AI tutor.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.95f),
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Quick Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatPill(
                                icon = Icons.Default.LocalFireDepartment,
                                label = "Streak",
                                value = "${userStreak?.currentStreakDays ?: 0} Days",
                                iconColor = ZakaAmber
                            )
                            StatPill(
                                icon = Icons.Default.CheckCircle,
                                label = "Topics",
                                value = "$completedCount Done",
                                iconColor = Color.White
                            )
                            StatPill(
                                icon = Icons.Default.Quiz,
                                label = "Average",
                                value = averageLabel,
                                iconColor = ZakaTeal
                            )
                        }
                    }
                }
            }
        }

        // AI Teacher Quick Launcher Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ai_teacher_card")
                    .clickable { onNavigateToAiTeacher(null) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(ZakaIndigo.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SmartToy,
                                    contentDescription = "AI Teacher",
                                    tint = ZakaIndigo
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "ZAKA AI Teacher",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "Active Provider: ${settings.providerType.displayName}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Open AI Teacher",
                            tint = ZakaIndigo
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Ask any Computer Science question in English or Roman Urdu:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val samplePrompts = listOf(
                            "What is RAM vs ROM?",
                            "ram kya hota ha samjhao",
                            "Convert 45 to binary",
                            "Explain CPU and ALU",
                            "Python for loop example"
                        )
                        items(samplePrompts) { prompt ->
                            SuggestionChip(
                                onClick = { onNavigateToAiTeacher(prompt) },
                                label = { Text(prompt, style = MaterialTheme.typography.bodySmall) },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }
                }
            }
        }

        // Quick Navigation Buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onNavigateToCourse,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("btn_all_course"),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Full Course")
                }

                OutlinedButton(
                    onClick = { onNavigateToQuiz(1) },
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("btn_practice_quiz"),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.Quiz, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("MCQ Quiz")
                }
            }
        }

        // Daily CS Challenge
        if (dailyQuestion != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("daily_challenge_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (dailyDone) "Daily CS Challenge — completed today" else "Daily CS Challenge",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = dailyQuestion.question,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        FilledTonalButton(
                            onClick = { onStartDailyChallenge(dailyQuestion) },
                            modifier = Modifier.testTag("btn_daily_challenge")
                        ) {
                            Text(if (dailyDone) "Try it again" else "Answer today's question")
                        }
                    }
                }
            }
        }

        // Study tools
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Study Tools",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ToolButton("Question Tester", Icons.Default.EditNote, Modifier.weight(1f).testTag("btn_question_tester"), onNavigateToTester)
                    ToolButton("Tests & Exams", Icons.Default.Assignment, Modifier.weight(1f).testTag("btn_exams"), onNavigateToExam)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ToolButton("My Progress", Icons.Default.Insights, Modifier.weight(1f).testTag("btn_progress"), onNavigateToProgress)
                    ToolButton("Notes & Glossary", Icons.Default.Description, Modifier.weight(1f).testTag("btn_notes"), onNavigateToNotes)
                }
                ToolButton(
                    "Error Solver",
                    Icons.Default.BugReport,
                    Modifier.fillMaxWidth().testTag("btn_error_solver"),
                    onNavigateToErrorSolver
                )
            }
        }

        // Chapters Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Class 9 Course Chapters",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                TextButton(onClick = onNavigateToCourse) {
                    Text("See All (8)")
                }
            }
        }

        // Chapter Cards
        val chapters = com.example.data.course.CourseRepository.allChapters
        items(chapters) { chapter ->
            DashboardChapterCard(
                chapter = chapter,
                onChapterClick = {
                    val firstTopic = chapter.topics.firstOrNull()
                    if (firstTopic != null) {
                        onNavigateToTopic(firstTopic.topicId)
                    }
                },
                onQuizClick = { onNavigateToQuiz(chapter.id) },
                onFlashcardClick = { onNavigateToFlashcards(chapter.id) }
            )
        }
    }
}

@Composable
private fun ToolButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, style = MaterialTheme.typography.labelLarge, maxLines = 1)
    }
}

@Composable
private fun StatPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    iconColor: Color
) {
    Surface(
        color = Color.White.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.75f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DashboardChapterCard(
    chapter: ChapterItem,
    onChapterClick: () -> Unit,
    onQuizClick: () -> Unit,
    onFlashcardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onChapterClick() }
            .testTag("chapter_card_${chapter.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ch ${chapter.number}",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = chapter.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onFlashcardClick,
                    modifier = Modifier.testTag("btn_fc_ch_${chapter.id}")
                ) {
                    Icon(Icons.Default.Style, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Flashcards", style = MaterialTheme.typography.labelMedium)
                }

                Spacer(modifier = Modifier.width(8.dp))

                FilledTonalButton(
                    onClick = onQuizClick,
                    modifier = Modifier.testTag("btn_quiz_ch_${chapter.id}")
                ) {
                    Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Quiz", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
