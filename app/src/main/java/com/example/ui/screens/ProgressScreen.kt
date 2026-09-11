package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.viewmodel.ZakaViewModel

/**
 * My Progress: only shows figures that come from recorded attempts. Chapters with no attempt say so
 * instead of displaying an invented score.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    viewModel: ZakaViewModel,
    onNavigateBack: () -> Unit,
    onStartRevision: () -> Unit,
    onStartChapterTest: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val snapshot by viewModel.learningSnapshot.collectAsState()
    val streak by viewModel.userStreak.collectAsState()
    val examResults by viewModel.examResults.collectAsState()
    val attempts by viewModel.writtenAttempts.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My Progress", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.testTag("progress_summary_card")) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        snapshot.overallScore?.let { "Overall average: $it%" } ?: "Overall average: no attempts recorded yet",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text("Recorded attempts: ${snapshot.totalAttempts}", style = MaterialTheme.typography.bodyMedium)
                    Text("Written answers checked: ${attempts.size}", style = MaterialTheme.typography.bodyMedium)
                    Text("Papers completed: ${examResults.size}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "Current streak: ${streak?.currentStreakDays ?: 0} day(s)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            SectionLabel("Chapter mastery")
            snapshot.chapters.forEach { chapter ->
                Card(shape = RoundedCornerShape(12.dp)) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(chapter.chapterTitle, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text(
                            "Topics read: ${chapter.topicsCompleted}/${chapter.topicsTotal}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (chapter.averageScore != null) {
                            LinearProgressIndicator(
                                progress = { chapter.averageScore / 100f },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                "Average ${chapter.averageScore}% across ${chapter.attempts} attempt(s)",
                                style = MaterialTheme.typography.bodySmall
                            )
                        } else {
                            Text(
                                "No attempts recorded yet",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        TextButton(onClick = { onStartChapterTest(chapter.chapterId) }) {
                            Text("Take chapter test")
                        }
                    }
                }
            }

            SectionLabel("Smart recommendations")
            snapshot.recommendations.forEach { Text("• $it", style = MaterialTheme.typography.bodyMedium) }

            Button(
                onClick = onStartRevision,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_start_revision"),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Start Smart Revision")
            }
        }
    }
}
