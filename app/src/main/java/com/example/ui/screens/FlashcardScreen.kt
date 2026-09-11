package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ZakaAmber
import com.example.ui.theme.ZakaIndigo
import com.example.ui.theme.ZakaTeal
import com.example.ui.viewmodel.ZakaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(
    viewModel: ZakaViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val flashcards by viewModel.currentFlashcards.collectAsState()
    val currentIndex by viewModel.flashcardIndex.collectAsState()
    val isFlipped by viewModel.isCardFlipped.collectAsState()
    val masteryList by viewModel.flashcardMastery.collectAsState()

    val currentCard = flashcards.getOrNull(currentIndex)
    val isCurrentMastered = currentCard?.let { card ->
        masteryList.any { it.flashcardId == card.id && it.isMastered }
    } ?: false

    // Smooth rotation animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(400),
        label = "cardFlip"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Active Recall Flashcards", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
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
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Chapter selector
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                items((1..8).toList()) { chNum ->
                    FilterChip(
                        selected = currentCard?.chapterId == chNum,
                        onClick = { viewModel.loadChapterFlashcards(chNum) },
                        label = { Text("Ch $chNum") }
                    )
                }
            }

            if (currentCard != null) {
                // Counter & Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Card ${currentIndex + 1} of ${flashcards.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (isCurrentMastered) {
                        Surface(
                            color = ZakaTeal.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ZakaTeal, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Mastered", style = MaterialTheme.typography.labelSmall, color = ZakaTeal)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Interactive 3D Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .graphicsLayer {
                            rotationY = rotation
                            cameraDistance = 12f * density
                        }
                        .clickable { viewModel.toggleCardFlip() }
                        .testTag("flashcard_flip_box"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (rotation <= 90f) {
                            // Front of Card (Question)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    color = ZakaIndigo.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = currentCard.topicTitle,
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = ZakaIndigo,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = currentCard.front,
                                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 32.sp
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.TouchApp,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Tap card to flip & reveal answer",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        } else {
                            // Back of Card (Answer) - Flipped
                            Column(
                                modifier = Modifier.graphicsLayer { rotationY = 180f },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Surface(
                                    color = ZakaTeal.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "ANSWER & REASONING",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = ZakaTeal,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = currentCard.back,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 26.sp
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Text(
                                    text = "Tap to flip back",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation Controls (Prev / Next)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.previousFlashcard() },
                        modifier = Modifier.testTag("btn_prev_flashcard")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Previous Card")
                    }

                    FilledTonalButton(
                        onClick = { viewModel.toggleCardFlip() },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Flip, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isFlipped) "Show Question" else "Reveal Answer")
                    }

                    IconButton(
                        onClick = { viewModel.nextFlashcard() },
                        modifier = Modifier.testTag("btn_next_flashcard")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next Card")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Mastery Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.markFlashcardMastery(isMastered = false) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_fc_still_learning"),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.HourglassEmpty, contentDescription = null, tint = ZakaAmber)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Still Learning")
                    }

                    Button(
                        onClick = { viewModel.markFlashcardMastery(isMastered = true) },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("btn_fc_mastered"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ZakaTeal)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Mastered!")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No flashcards found.")
                }
            }
        }
    }
}
