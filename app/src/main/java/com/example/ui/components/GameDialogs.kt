package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.GameRepository
import com.example.model.Achievement
import com.example.model.PuzzleLevel
import com.example.ui.theme.FailRed
import com.example.ui.theme.GoldStar
import com.example.ui.theme.InkBlack
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelRed
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.PatrickHandFontFamily
import com.example.ui.theme.StickyNoteYellow
import com.example.ui.theme.SuccessGreen

/**
 * Progressive Hint Dialog styled as a post-it sticky note pinned to the notebook.
 */
@Composable
fun HintStickyNoteDialog(
    level: PuzzleLevel,
    hintsAvailable: Int,
    onUseHint: () -> Unit,
    onDismiss: () -> Unit
) {
    var revealedTier by remember { mutableIntStateOf(1) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .rotate(-2f)
                .shadow(16.dp, RoundedCornerShape(8.dp))
                .background(StickyNoteYellow, RoundedCornerShape(8.dp))
                .border(BorderStroke(2.5.dp, InkBlack), RoundedCornerShape(8.dp))
                .padding(20.dp)
                .testTag("hint_dialog")
        ) {
            // Pushpin at top center
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (-30).dp)
                    .size(24.dp)
                    .background(FailRed, CircleShape)
                    .border(BorderStroke(2.dp, InkBlack), CircleShape)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💡 Brain Hint",
                        fontFamily = PatrickHandFontFamily,
                        fontSize = 24.sp,
                        color = InkBlack
                    )
                    Text(
                        text = "💡 x$hintsAvailable",
                        fontFamily = PatrickHandFontFamily,
                        fontSize = 18.sp,
                        color = InkBlack
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Hints List (Progressive)
                for (tier in 1..revealedTier) {
                    val hintText = level.hints.getOrNull(tier - 1) ?: "Think carefully about what you see!"
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(Color(0xFFFFFDE7), RoundedCornerShape(6.dp))
                            .border(BorderStroke(1.dp, Color(0xFFFFEE58)), RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Clue $tier: $hintText",
                            fontFamily = PatrickHandFontFamily,
                            fontSize = 16.sp,
                            color = InkBlack
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (revealedTier < level.hints.size && hintsAvailable > 0) {
                        CartoonButton(
                            text = "Next Clue (-1 💡)",
                            backgroundColor = PastelYellow,
                            onClick = {
                                revealedTier++
                                onUseHint()
                            },
                            testTag = "reveal_next_clue_btn"
                        )
                    }

                    CartoonButton(
                        text = "Got It! 👍",
                        backgroundColor = PastelGreen,
                        onClick = onDismiss,
                        testTag = "dismiss_hint_btn"
                    )
                }
            }
        }
    }
}

/**
 * Level Selector Grid styled as a notebook sticker sheet.
 */
@Composable
fun LevelSelectDialog(
    totalLevels: Int,
    currentLevelIndex: Int,
    repository: GameRepository,
    onSelectLevel: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(460.dp)
                .shadow(16.dp, RoundedCornerShape(16.dp))
                .background(Color(0xFFFFFDF8), RoundedCornerShape(16.dp))
                .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(16.dp))
                .padding(16.dp)
                .testTag("level_select_dialog")
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📚 Level Map",
                        fontFamily = PatrickHandFontFamily,
                        fontSize = 26.sp,
                        color = InkBlack
                    )
                    CartoonIconButton(onClick = onDismiss, size = 36.dp) {
                        Text("✕", fontSize = 16.sp, color = InkBlack)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(totalLevels) { index ->
                        val levelNum = index + 1
                        val isUnlocked = repository.isLevelUnlocked(index)
                        val stars = repository.getLevelStars(levelNum)
                        val isCurrent = index == currentLevelIndex

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    when {
                                        isCurrent -> PastelYellow
                                        isUnlocked -> Color.White
                                        else -> Color(0xFFE0E0E0)
                                    },
                                    RoundedCornerShape(10.dp)
                                )
                                .border(
                                    BorderStroke(
                                        if (isCurrent) 2.5.dp else 1.5.dp,
                                        if (isCurrent) InkBlack else Color.Gray
                                    ),
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable(enabled = isUnlocked) {
                                    onSelectLevel(index)
                                    onDismiss()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                if (isUnlocked) {
                                    Text(
                                        text = "$levelNum",
                                        fontFamily = PatrickHandFontFamily,
                                        fontSize = 17.sp,
                                        color = InkBlack
                                    )
                                    if (stars > 0) {
                                        Text(
                                            text = "⭐".repeat(stars),
                                            fontSize = 9.sp
                                        )
                                    }
                                } else {
                                    Text("🔒", fontSize = 16.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Settings Dialog (Audio, Music, Haptics, Reset)
 */
@Composable
fun SettingsDialog(
    repository: GameRepository,
    onSoundChanged: (Boolean) -> Unit,
    onMusicChanged: (Boolean) -> Unit,
    onHapticsChanged: (Boolean) -> Unit,
    onResetProgress: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(16.dp))
                .background(Color(0xFFFFFDF8), RoundedCornerShape(16.dp))
                .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(16.dp))
                .padding(20.dp)
                .testTag("settings_dialog")
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "⚙️ Game Settings",
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 28.sp,
                    color = InkBlack
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sound FX
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🔊 Cartoon Sound Effects", fontFamily = PatrickHandFontFamily, fontSize = 18.sp)
                    Switch(
                        checked = repository.soundFxEnabled,
                        onCheckedChange = {
                            repository.soundFxEnabled = it
                            onSoundChanged(it)
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = InkBlack, checkedTrackColor = PastelGreen)
                    )
                }

                // Music
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🎵 Background Music", fontFamily = PatrickHandFontFamily, fontSize = 18.sp)
                    Switch(
                        checked = repository.musicEnabled,
                        onCheckedChange = {
                            repository.musicEnabled = it
                            onMusicChanged(it)
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = InkBlack, checkedTrackColor = PastelGreen)
                    )
                }

                // Haptics
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📳 Haptic Vibrations", fontFamily = PatrickHandFontFamily, fontSize = 18.sp)
                    Switch(
                        checked = repository.hapticsEnabled,
                        onCheckedChange = {
                            repository.hapticsEnabled = it
                            onHapticsChanged(it)
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = InkBlack, checkedTrackColor = PastelGreen)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Reset progress
                CartoonButton(
                    text = "⚠️ Reset Progress",
                    backgroundColor = PastelRed,
                    onClick = {
                        onResetProgress()
                        onDismiss()
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                CartoonButton(
                    text = "Close",
                    backgroundColor = PastelGreen,
                    onClick = onDismiss
                )
            }
        }
    }
}

/**
 * Achievements Dialog showing badge trophies
 */
@Composable
fun AchievementsDialog(
    achievements: List<Achievement>,
    brainIq: Int,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(480.dp)
                .shadow(16.dp, RoundedCornerShape(16.dp))
                .background(Color(0xFFFFFDF8), RoundedCornerShape(16.dp))
                .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(16.dp))
                .padding(16.dp)
                .testTag("achievements_dialog")
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("🏆 Brain Trophy Shelf", fontFamily = PatrickHandFontFamily, fontSize = 24.sp, color = InkBlack)
                        Text("Brain IQ: $brainIq", fontFamily = PatrickHandFontFamily, fontSize = 16.sp, color = SuccessGreen)
                    }
                    CartoonIconButton(onClick = onDismiss, size = 36.dp) {
                        Text("✕", fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    itemsIndexed(achievements) { _, item ->
                        Box(
                            modifier = Modifier
                                .background(
                                    if (item.isUnlocked) PastelYellow else Color(0xFFF5F5F5),
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    BorderStroke(
                                        if (item.isUnlocked) 2.dp else 1.dp,
                                        if (item.isUnlocked) InkBlack else Color.LightGray
                                    ),
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(10.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(item.iconEmoji, fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = item.title,
                                        fontFamily = PatrickHandFontFamily,
                                        fontSize = 15.sp,
                                        color = if (item.isUnlocked) InkBlack else Color.Gray
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.description,
                                    fontFamily = PatrickHandFontFamily,
                                    fontSize = 12.sp,
                                    color = if (item.isUnlocked) InkBlack else Color.Gray
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (item.isUnlocked) "✅ UNLOCKED" else "${item.progress}/${item.maxProgress}",
                                    fontFamily = PatrickHandFontFamily,
                                    fontSize = 11.sp,
                                    color = if (item.isUnlocked) SuccessGreen else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
