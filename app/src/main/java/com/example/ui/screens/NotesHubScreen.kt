package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.course.CourseRepository
import com.example.model.TopicNote

private enum class NotesView(val label: String) {
    COMPLETE("Complete Notes"),
    EXAM("Exam Notes"),
    QUICK("Quick Revision"),
    DEFINITIONS("Definitions"),
    IMPORTANT("Important Questions"),
    GLOSSARY("Glossary")
}

/**
 * Notes hub: the same course notes presented in the format the student needs right now.
 * Everything shown comes from the existing chapter notes — nothing is generated here.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesHubScreen(
    onNavigateBack: () -> Unit,
    onOpenTopic: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var view by remember { mutableStateOf(NotesView.COMPLETE) }
    var chapterId by remember { mutableStateOf<Int?>(null) }

    val topics: List<TopicNote> = remember(chapterId) {
        chapterId?.let { id -> CourseRepository.getAllTopics().filter { it.chapterId == id } }
            ?: CourseRepository.getAllTopics()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Notes & Glossary", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                FlowChips {
                    NotesView.entries.forEach { option ->
                        FilterChip(
                            selected = view == option,
                            onClick = { view = option },
                            label = { Text(option.label) },
                            modifier = Modifier.testTag("notes_view_${option.name.lowercase()}")
                        )
                    }
                }
            }

            item {
                FlowChips {
                    FilterChip(
                        selected = chapterId == null,
                        onClick = { chapterId = null },
                        label = { Text("All") }
                    )
                    CourseRepository.allChapters.forEach { chapter ->
                        FilterChip(
                            selected = chapterId == chapter.id,
                            onClick = { chapterId = chapter.id },
                            label = { Text("Ch ${chapter.number}") }
                        )
                    }
                }
            }

            if (view == NotesView.GLOSSARY) {
                val glossary = topics
                    .flatMap { topic -> topic.keyTerms.map { it.first to it.second } }
                    .distinctBy { it.first.lowercase() }
                    .sortedBy { it.first.lowercase() }

                items(glossary) { (term, meaning) ->
                    Card(shape = RoundedCornerShape(12.dp)) {
                        Column(Modifier.padding(12.dp)) {
                            Text(term, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text(meaning, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            } else {
                items(topics) { topic ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.testTag("notes_topic_${topic.topicId}")
                    ) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(topic.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text(
                                topic.chapterTitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            when (view) {
                                NotesView.COMPLETE -> {
                                    Text(topic.definition, style = MaterialTheme.typography.bodyMedium)
                                    Text(topic.easyExplanation, style = MaterialTheme.typography.bodySmall)
                                    topic.importantPoints.take(5).forEach {
                                        Text("• $it", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                                NotesView.EXAM -> topic.examFocusedPoints.forEach {
                                    Text("✓ $it", style = MaterialTheme.typography.bodySmall)
                                }
                                NotesView.QUICK -> Text(topic.revisionSummary, style = MaterialTheme.typography.bodyMedium)
                                NotesView.DEFINITIONS -> {
                                    Text(topic.definition, style = MaterialTheme.typography.bodyMedium)
                                    topic.keyTerms.take(6).forEach { (term, meaning) ->
                                        Text("$term — $meaning", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                                NotesView.IMPORTANT -> {
                                    (topic.shortQuestions + topic.longQuestions).take(6).forEach { qa ->
                                        Text("Q: ${qa.question}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                                        Text("A: ${qa.answer}", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                                NotesView.GLOSSARY -> Unit
                            }

                            TextButton(onClick = { onOpenTopic(topic.topicId) }) {
                                Text("Open full topic")
                            }
                        }
                    }
                }
            }
        }
    }
}
