package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.viewmodel.ZakaViewModel

/**
 * Error Solver: paste an error or snippet and get a structured static analysis that separates
 * observed facts from likely causes. The app does not execute code, and says so.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorSolverScreen(
    viewModel: ZakaViewModel,
    onNavigateBack: () -> Unit,
    onAskAi: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val analysis by viewModel.errorAnalysis.collectAsState()
    var input by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Error Solver", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
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
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Paste the error message or the failing code") },
                minLines = 6,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("error_input_field")
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { viewModel.analyzeError(input) },
                    enabled = input.isNotBlank(),
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("btn_analyze_error"),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Analyze")
                }
                OutlinedButton(
                    onClick = { onAskAi("Explain and fix this error:\n$input") },
                    enabled = input.isNotBlank(),
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Ask ZAKA AI")
                }
            }

            analysis?.let { result ->
                Card(shape = RoundedCornerShape(16.dp), modifier = Modifier.testTag("error_analysis_card")) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (result.observedFacts.isNotEmpty()) {
                            SectionLabel("Observed in your text")
                            result.observedFacts.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }
                        }

                        SectionLabel("Likely causes")
                        result.likelyCauses.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }

                        SectionLabel("Suggested fixes")
                        result.suggestedFixes.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }

                        SectionLabel("Needs an actual run to confirm")
                        result.needsExecution.forEach { Text("• $it", style = MaterialTheme.typography.bodySmall) }

                        HorizontalDivider()
                        Text(
                            "${result.executionNotice}\nAnalyzed by: ${result.analyzedBy}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
