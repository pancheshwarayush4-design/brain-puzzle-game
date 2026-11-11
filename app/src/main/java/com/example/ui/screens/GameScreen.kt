package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AchievementsDialog
import com.example.ui.components.CartoonButton
import com.example.ui.components.CartoonIconButton
import com.example.ui.components.CartoonLightbulb
import com.example.ui.components.HintStickyNoteDialog
import com.example.ui.components.LevelSelectDialog
import com.example.ui.components.NotebookScaffold
import com.example.ui.components.SettingsDialog
import com.example.ui.components.VictoryCelebrationModal
import com.example.ui.components.WrongAttemptOverlay
import com.example.ui.puzzles.InteractivePuzzleDispatcher
import com.example.ui.theme.FailRed
import com.example.ui.theme.GoldStar
import com.example.ui.theme.InkBlack
import com.example.ui.theme.LightbulbGlow
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelOrange
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.PatrickHandFontFamily
import com.example.ui.theme.StickyNoteYellow

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    NotebookScaffold(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                // 1. TOP SECTION: Settings, Level Map, LEVEL badge, Hint bulb
                TopBarSection(
                    levelNumber = state.currentLevel.levelNumber,
                    categoryName = state.currentLevel.category.displayName,
                    categoryIcon = state.currentLevel.category.iconLabel,
                    hintsCount = state.hintsCount,
                    brainIq = state.brainIq,
                    onOpenSettings = viewModel::openSettings,
                    onOpenLevelSelect = viewModel::openLevelSelect,
                    onOpenHints = viewModel::openHintDialog,
                    onOpenAchievements = viewModel::openAchievements
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 2. PUZZLE QUESTION SECTION: Prominently displayed in Patrick Hand font
                QuestionSection(
                    question = state.currentLevel.question,
                    subClue = state.currentLevel.subClue
                )

                // 3. MAIN PUZZLE INTERACTIVE AREA
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    InteractivePuzzleDispatcher(
                        level = state.currentLevel,
                        soundManager = viewModel.soundManager,
                        onSolved = viewModel::onLevelSolved,
                        onWrongAttempt = viewModel::onWrongAttempt
                    )

                    // Wrong attempt banner overlay
                    if (state.wrongAttemptMessage.isNotEmpty() && state.showWrongAttemptOverlay) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp)
                                .shadow(8.dp, RoundedCornerShape(10.dp))
                                .background(Color(0xFFFFEBEE), RoundedCornerShape(10.dp))
                                .border(BorderStroke(2.dp, FailRed), RoundedCornerShape(10.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "💡 ${state.wrongAttemptMessage}",
                                fontFamily = PatrickHandFontFamily,
                                fontSize = 16.sp,
                                color = FailRed
                            )
                        }
                    }
                }

                // 4. BOTTOM SECTION: Hint, Rewards/Daily, Restart, Next buttons
                BottomBarSection(
                    hintsCount = state.hintsCount,
                    onOpenHint = viewModel::openHintDialog,
                    onDailyChallenge = viewModel::openDailyChallenge,
                    onRestart = viewModel::replayLevel,
                    onSkip = viewModel::nextLevel
                )
            }

            // Wrong Attempt Red Cross Stamp Animation
            WrongAttemptOverlay(visible = state.showWrongAttemptOverlay)

            // Victory Celebration Modal
            if (state.showVictoryModal) {
                VictoryCelebrationModal(
                    levelNumber = state.currentLevel.levelNumber,
                    stars = state.starsForCurrentLevel,
                    explanation = state.currentLevel.explanation,
                    onNextLevel = viewModel::nextLevel,
                    onReplay = viewModel::replayLevel
                )
            }

            // Hint Sticky Note Dialog
            if (state.showHintDialog) {
                HintStickyNoteDialog(
                    level = state.currentLevel,
                    hintsAvailable = state.hintsCount,
                    onUseHint = viewModel::useHint,
                    onDismiss = viewModel::dismissHintDialog
                )
            }

            // Level Map Dialog
            if (state.showLevelSelectDialog) {
                LevelSelectDialog(
                    totalLevels = state.totalLevels,
                    currentLevelIndex = state.currentLevelIndex,
                    repository = viewModel.repository,
                    onSelectLevel = viewModel::loadLevel,
                    onDismiss = viewModel::dismissLevelSelect
                )
            }

            // Settings Dialog
            if (state.showSettingsDialog) {
                SettingsDialog(
                    repository = viewModel.repository,
                    onSoundChanged = viewModel::onSoundChanged,
                    onMusicChanged = viewModel::onMusicChanged,
                    onHapticsChanged = viewModel::onHapticsChanged,
                    onResetProgress = viewModel::resetProgress,
                    onDismiss = viewModel::dismissSettings
                )
            }

            // Achievements Dialog
            if (state.showAchievementsDialog) {
                AchievementsDialog(
                    achievements = state.achievements,
                    brainIq = state.brainIq,
                    onDismiss = viewModel::dismissAchievements
                )
            }
        }
    }
}

/**
 * 1. Top Section
 */
@Composable
private fun TopBarSection(
    levelNumber: Int,
    categoryName: String,
    categoryIcon: String,
    hintsCount: Int,
    brainIq: Int,
    onOpenSettings: () -> Unit,
    onOpenLevelSelect: () -> Unit,
    onOpenHints: () -> Unit,
    onOpenAchievements: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Settings & Level Map
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CartoonIconButton(onClick = onOpenSettings, size = 42.dp, testTag = "settings_icon_btn") {
                Text("⚙️", fontSize = 20.sp)
            }
            CartoonIconButton(onClick = onOpenLevelSelect, size = 42.dp, testTag = "menu_icon_btn") {
                Text("📋", fontSize = 20.sp)
            }
        }

        // Center: LEVEL Badge styled as a cute paper sticker
        Box(
            modifier = Modifier
                .rotate(-1.5f)
                .background(PastelYellow, RoundedCornerShape(12.dp))
                .border(BorderStroke(2.5.dp, InkBlack), RoundedCornerShape(12.dp))
                .padding(horizontal = 14.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "LEVEL $levelNumber",
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 20.sp,
                    color = InkBlack
                )
                Text(
                    text = "$categoryIcon $categoryName",
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 12.sp,
                    color = Color(0xFF616161)
                )
            }
        }

        // Right: Brain IQ & Hint Bulb
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            // Brain IQ badge
            Box(
                modifier = Modifier
                    .rotate(1.5f)
                    .background(Color(0xFFE8F5E9), RoundedCornerShape(12.dp))
                    .border(BorderStroke(2.dp, InkBlack), RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🧠 $brainIq",
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 15.sp,
                    color = InkBlack
                )
            }

            // Hint Bulb Button with Counter
            CartoonIconButton(
                onClick = onOpenHints,
                size = 42.dp,
                backgroundColor = LightbulbGlow,
                testTag = "hint_bulb_btn"
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CartoonLightbulb(glow = hintsCount > 0)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 6.dp, y = 6.dp)
                            .background(FailRed, CircleShape)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "$hintsCount",
                            fontFamily = PatrickHandFontFamily,
                            fontSize = 10.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

/**
 * 2. Prominent Question Section
 */
@Composable
private fun QuestionSection(
    question: String,
    subClue: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = question,
                fontFamily = PatrickHandFontFamily,
                fontSize = 25.sp,
                color = InkBlack,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )

            if (subClue.isNotEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "✎ $subClue",
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 15.sp,
                    color = Color(0xFF757575),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * 4. Bottom Section
 */
@Composable
private fun BottomBarSection(
    hintsCount: Int,
    onOpenHint: () -> Unit,
    onDailyChallenge: () -> Unit,
    onRestart: () -> Unit,
    onSkip: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hint Button
        CartoonButton(
            text = "Hint 💡",
            backgroundColor = PastelYellow,
            onClick = onOpenHint,
            testTag = "bottom_hint_button"
        )

        // Daily Challenge Button
        CartoonButton(
            text = "Daily 📅",
            backgroundColor = PastelBlue,
            onClick = onDailyChallenge,
            testTag = "bottom_daily_button"
        )

        // Restart Button
        CartoonIconButton(
            onClick = onRestart,
            size = 44.dp,
            backgroundColor = Color.White,
            testTag = "bottom_restart_button"
        ) {
            Text("🔄", fontSize = 18.sp)
        }

        // Skip / Next Button
        CartoonButton(
            text = "Skip ⏩",
            backgroundColor = PastelGreen,
            onClick = onSkip,
            testTag = "bottom_skip_button"
        )
    }
}
