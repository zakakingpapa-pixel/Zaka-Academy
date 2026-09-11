package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TopicNote
import com.example.ui.theme.ZakaAmber
import com.example.ui.theme.ZakaIndigo
import com.example.ui.theme.ZakaRose
import com.example.ui.theme.ZakaTeal
import com.example.ui.viewmodel.ZakaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicDetailScreen(
    topic: TopicNote?,
    viewModel: ZakaViewModel,
    onNavigateBack: () -> Unit,
    onAskAiAboutTopic: (String) -> Unit,
    onTakeQuiz: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (topic == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Topic not found.")
        }
        return
    }

    val progressList by viewModel.topicProgress.collectAsState()
    val isBookmarked = progressList.any { it.topicId == topic.topicId && it.isBookmarked }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = topic.chapterTitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = topic.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.toggleBookmark(topic.topicId, topic.chapterId, isBookmarked) },
                        modifier = Modifier.testTag("btn_bookmark")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) ZakaAmber else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // 1. Definition Card
            item {
                NoteSectionCard(
                    title = "1. Scientific Definition",
                    icon = Icons.Default.Lightbulb,
                    accentColor = ZakaIndigo
                ) {
                    Text(
                        text = topic.definition,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp
                    )
                }
            }

            // 2. Easy Explanation & Analogy
            item {
                NoteSectionCard(
                    title = "2. Easy Explanation & Analogy",
                    icon = Icons.Default.SentimentSatisfied,
                    accentColor = ZakaTeal
                ) {
                    Text(
                        text = topic.easyExplanation,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp
                    )
                }
            }

            // 3. Detailed Technical Explanation
            item {
                NoteSectionCard(
                    title = "3. Detailed Architecture & Technical Mechanism",
                    icon = Icons.Default.AccountTree,
                    accentColor = ZakaIndigo
                ) {
                    Text(
                        text = topic.detailedExplanation,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 22.sp
                    )
                }
            }

            // 4. Visual Diagram (Monospace ASCII Art)
            if (topic.visualDiagram != null) {
                item {
                    NoteSectionCard(
                        title = "4. Architecture Flow Diagram",
                        icon = Icons.Default.Brush,
                        accentColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .horizontalScroll(rememberScrollState())
                                .padding(12.dp)
                        ) {
                            Text(
                                text = topic.visualDiagram,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // 5. Real-World & Technical Examples
            item {
                NoteSectionCard(
                    title = "5. Concrete Examples",
                    icon = Icons.Default.Explore,
                    accentColor = ZakaAmber
                ) {
                    Text(
                        text = "Real-World Context:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = topic.realWorldExample, style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Technical / Code Context:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(text = topic.technicalExample, style = MaterialTheme.typography.bodyMedium)
                }
            }

            // 6. Important Points
            item {
                NoteSectionCard(
                    title = "6. Core Important Points",
                    icon = Icons.Default.Checklist,
                    accentColor = ZakaTeal
                ) {
                    topic.importantPoints.forEach { point ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("• ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(text = point, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // 7. Key Terms Table
            item {
                NoteSectionCard(
                    title = "7. Key Terminology",
                    icon = Icons.Default.MenuBook,
                    accentColor = ZakaIndigo
                ) {
                    topic.keyTerms.forEach { (term, definition) ->
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(
                                text = term,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = definition,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }

            // 8. Advantages & Disadvantages
            item {
                NoteSectionCard(
                    title = "8. Advantages & Disadvantages",
                    icon = Icons.Default.CompareArrows,
                    accentColor = ZakaTeal
                ) {
                    Text(
                        text = "Advantages:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = ZakaTeal
                    )
                    topic.advantages.forEach { adv ->
                        Text("✓ $adv", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Disadvantages & Limitations:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = ZakaRose
                    )
                    topic.disadvantages.forEach { dis ->
                        Text("✗ $dis", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 2.dp))
                    }
                }
            }

            // 9. Common Mistakes & Exam Pitfalls
            item {
                NoteSectionCard(
                    title = "9. Common Pitfalls & Mistakes",
                    icon = Icons.Default.Warning,
                    accentColor = ZakaRose
                ) {
                    topic.commonMistakes.forEach { mistake ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("⚠️ ", modifier = Modifier.padding(top = 2.dp))
                            Text(text = mistake, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // 10. Board Exam Focused Points
            item {
                NoteSectionCard(
                    title = "10. Board Exam Focused Pointers",
                    icon = Icons.Default.Stars,
                    accentColor = ZakaAmber
                ) {
                    topic.examFocusedPoints.forEach { pt ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("★ ", color = ZakaAmber, fontWeight = FontWeight.Bold)
                            Text(text = pt, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // 11. Short & Long Practice Questions
            item {
                NoteSectionCard(
                    title = "11. Exam Practice Questions (Short & Long)",
                    icon = Icons.Default.Quiz,
                    accentColor = ZakaIndigo
                ) {
                    Text(
                        text = "Short Answer Questions:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    topic.shortQuestions.forEachIndexed { idx, q ->
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Text(
                                text = "Q${idx + 1}: ${q.question}",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                text = "Ans: ${q.answer}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    if (topic.longQuestions.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Long Answer Question:",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        val lq = topic.longQuestions.first()
                        Text(
                            text = "Q: ${lq.question}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = lq.answer,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // 12. Quick Revision Summary
            item {
                NoteSectionCard(
                    title = "12. Quick Revision Summary",
                    icon = Icons.Default.Bolt,
                    accentColor = ZakaAmber
                ) {
                    Text(
                        text = topic.revisionSummary,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Action Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onAskAiAboutTopic("Explain ${topic.title} in simple Roman Urdu with examples") },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("btn_ask_ai_topic"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.SmartToy, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ask AI Tutor")
                    }

                    FilledTonalButton(
                        onClick = { onTakeQuiz(topic.chapterId) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("btn_quiz_topic"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.Quiz, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Take Quiz")
                    }
                }
            }
        }
    }
}

@Composable
fun NoteSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = accentColor
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(10.dp))
            content()
        }
    }
}
