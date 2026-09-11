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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AiProviderType
import com.example.model.TeachingStyle
import com.example.ui.theme.ZakaIndigo
import com.example.ui.theme.ZakaTeal
import com.example.ui.viewmodel.ZakaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: ZakaViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentSettings by viewModel.settings.collectAsState()

    var selectedProvider by remember(currentSettings) { mutableStateOf(currentSettings.providerType) }
    var geminiKey by remember(currentSettings) { mutableStateOf(currentSettings.geminiApiKey) }
    var ollamaEndpoint by remember(currentSettings) { mutableStateOf(currentSettings.ollamaEndpoint) }
    var ollamaModel by remember(currentSettings) { mutableStateOf(currentSettings.ollamaModel) }
    var selectedStyle by remember(currentSettings) { mutableStateOf(currentSettings.teachingStyle) }
    var showKey by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings & AI Engine", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // AI Provider Selection
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "AI Teacher Engine Provider",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Select how the AI Teacher resolves questions:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    AiProviderType.values().filter { it != AiProviderType.OPENAI_COMPATIBLE }.forEach { provider ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedProvider == provider,
                                onClick = {
                                    selectedProvider = provider
                                    viewModel.updateSettings(
                                        currentSettings.copy(providerType = provider)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = provider.displayName,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                                )
                                Text(
                                    text = when (provider) {
                                        AiProviderType.OFFLINE_EXPERT -> "100% Offline, verified textbook knowledge, zero internet required (Fastest)"
                                        AiProviderType.GEMINI_CLOUD -> "Uses Google Gemini Flash model via API key"
                                        AiProviderType.OLLAMA_LAN -> "Connects to local Ollama on your PC/Mac over Wi-Fi"
                                        else -> ""
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Gemini API Key (if selected)
            if (selectedProvider == AiProviderType.GEMINI_CLOUD) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Google Gemini Configuration",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = ZakaIndigo
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Enter your Google AI Studio Gemini API Key. Can also be set in AI Studio Secrets panel.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = geminiKey,
                            onValueChange = {
                                geminiKey = it
                                viewModel.updateSettings(currentSettings.copy(geminiApiKey = it))
                            },
                            label = { Text("Gemini API Key") },
                            visualTransformation = if (showKey) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { showKey = !showKey }) {
                                    Icon(
                                        imageVector = if (showKey) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Toggle Visibility"
                                    )
                                }
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("gemini_api_key_input")
                        )
                    }
                }
            }

            // Ollama LAN Configuration (if selected)
            if (selectedProvider == AiProviderType.OLLAMA_LAN) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Local Ollama LAN Configuration",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = ZakaTeal
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Ensure your PC and phone are on the same Wi-Fi. Run: OLLAMA_HOST=0.0.0.0 ollama serve",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = ollamaEndpoint,
                            onValueChange = {
                                ollamaEndpoint = it
                                viewModel.updateSettings(currentSettings.copy(ollamaEndpoint = it))
                            },
                            label = { Text("Ollama Endpoint URL") },
                            placeholder = { Text("http://192.168.1.100:11434") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = ollamaModel,
                            onValueChange = {
                                ollamaModel = it
                                viewModel.updateSettings(currentSettings.copy(ollamaModel = it))
                            },
                            label = { Text("Model Name") },
                            placeholder = { Text("qwen2.5-coder:7b or llama3:8b") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Teaching Style
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "AI Teaching Style",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TeachingStyle.values().forEach { style ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedStyle == style,
                                onClick = {
                                    selectedStyle = style
                                    viewModel.updateSettings(currentSettings.copy(teachingStyle = style))
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = when (style) {
                                    TeachingStyle.SIMPLE -> "Simple & Easy (Plain language with analogies)"
                                    TeachingStyle.BALANCED -> "Standard Academic (Textbook definitions & exam points)"
                                    TeachingStyle.ADVANCED -> "Deep Technical (Internal architecture & mechanism)"
                                    TeachingStyle.DEBUGGING -> "Code & Debugging (Error diagnosis & step-by-step logic)"
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            // Platform Details Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "About ZAKA ACADEMY",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• Course: Class 9 Computer Science Complete Curriculum (8 Chapters)\n" +
                               "• 22-part structured textbook notes with ASCII flow diagrams\n" +
                               "• 100% Offline Knowledge Layer & RAG Indexing\n" +
                               "• Multi-Provider AI Architecture with Anti-Hang Failover\n" +
                               "• Database: Local Room DB for progress, quizzes & flashcard mastery",
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
