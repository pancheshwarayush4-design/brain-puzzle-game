package com.example.ui.puzzles

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.SoundManager
import com.example.model.PuzzleLevel
import com.example.model.PuzzleType
import com.example.ui.components.CartoonButton
import com.example.ui.components.CartoonStar
import com.example.ui.theme.FailRed
import com.example.ui.theme.GoldStar
import com.example.ui.theme.InkBlack
import com.example.ui.theme.LightbulbGlow
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelGreenDark
import com.example.ui.theme.PastelOrange
import com.example.ui.theme.PastelPink
import com.example.ui.theme.PastelRed
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.PastelYellowDark
import com.example.ui.theme.PatrickHandFontFamily
import com.example.ui.theme.SuccessGreen
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun InteractivePuzzleDispatcher(
    level: PuzzleLevel,
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (message: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("interactive_puzzle_area")
    ) {
        when (level.puzzleType) {
            PuzzleType.FRUIT_SCALE -> FruitScalePuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.FROG_CROSSING -> FrogCrossingPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.LIGHTBULB_CIRCUIT -> LightbulbCircuitPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.MATCHSTICK_MATH -> MatchstickMathPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.SLEEPY_CAT -> SleepyStudentPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.ELEPHANT_FRIDGE -> ElephantFridgePuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.BALLOON_CACTUS -> BalloonCactusPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.HIDDEN_KEY -> HiddenKeyPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.SHAVE_GRANDPA -> ShaveGrandpaPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.HIGHEST_NUMBER -> HighestNumberPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.GIRAFFE_NECK -> GiraffeNeckPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.HATCH_CHICK -> HatchChickPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.PIPE_CONNECT -> PipeConnectPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.BALANCE_SEESAW -> BalanceSeesawPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.ODD_OWL_OUT -> OddOwlPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.FLASHLIGHT_DARK -> FlashlightDarkPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.DONUT_WHEEL -> DonutWheelPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.PRISON_ESCAPE -> PrisonEscapePuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.CATCH_APPLES -> CatchApplesPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.TSHIRT_HOLES -> TShirtHolesPuzzle(level, soundManager, onSolved, onWrongAttempt)
            PuzzleType.WELL_KITTEN -> WellKittenPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.TRAFFIC_LIGHT -> TrafficLightPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.FIND_STARS -> FindStarsPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.MONSTER_BED -> MonsterBedPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.VAULT_UNLOCKED -> VaultUnlockedPuzzle(soundManager, onSolved, onWrongAttempt)
            PuzzleType.GENERATED_TRICK -> GeneratedTrickPuzzle(level, soundManager, onSolved, onWrongAttempt)
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 1: FRUIT_SCALE ("Which one is the BIGGEST?")
// ----------------------------------------------------------------------------
@Composable
fun FruitScalePuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var watermelonScale by remember { mutableFloatStateOf(1f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Glowing Sun in upper sky (THE TRICK SOLUTION!)
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 10.dp, end = 20.dp)
                .size(76.dp)
                .clickable {
                    soundManager.playVictory()
                    onSolved()
                }
                .testTag("sun_target"),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(color = LightbulbGlow, radius = size.width * 0.38f)
                drawCircle(color = InkBlack, radius = size.width * 0.38f, style = Stroke(3.dp.toPx()))
                // Sun rays
                for (i in 0 until 8) {
                    val angle = (i * 45).toDouble() * Math.PI / 180.0
                    val r1 = size.width * 0.42f
                    val r2 = size.width * 0.48f
                    val cx = size.width / 2f
                    val cy = size.height / 2f
                    drawLine(
                        color = InkBlack,
                        start = Offset((cx + r1 * kotlin.math.cos(angle)).toFloat(), (cy + r1 * kotlin.math.sin(angle)).toFloat()),
                        end = Offset((cx + r2 * kotlin.math.cos(angle)).toFloat(), (cy + r2 * kotlin.math.sin(angle)).toFloat()),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
            Text("SUN", fontFamily = PatrickHandFontFamily, fontSize = 12.sp, color = InkBlack)
        }

        // Fruits and Animals on page
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Apple
                CartoonCardItem(
                    title = "Apple",
                    emoji = "🍎",
                    bg = PastelRed,
                    onClick = {
                        soundManager.playBuzzer()
                        onWrongAttempt("Tasty, but an apple is small in real life!")
                    }
                )

                // Mouse
                CartoonCardItem(
                    title = "Mouse",
                    emoji = "🐭",
                    bg = Color(0xFFCFD8DC),
                    onClick = {
                        soundManager.playBuzzer()
                        onWrongAttempt("A mouse is tiny! Eek!")
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Watermelon (can also be enlarged by tapping repeatedly!)
                Box(
                    modifier = Modifier
                        .scale(watermelonScale)
                        .clickable {
                            if (watermelonScale < 1.6f) {
                                watermelonScale += 0.3f
                                soundManager.playBoing()
                                if (watermelonScale >= 1.6f) {
                                    soundManager.playVictory()
                                    onSolved()
                                } else {
                                    onWrongAttempt("It's growing bigger! Keep tapping!")
                                }
                            }
                        }
                ) {
                    CartoonCardItem(
                        title = "Watermelon",
                        emoji = "🍉",
                        bg = PastelGreen,
                        onClick = {}
                    )
                }

                // Elephant
                CartoonCardItem(
                    title = "Elephant",
                    emoji = "🐘",
                    bg = PastelBlue,
                    onClick = {
                        soundManager.playBuzzer()
                        onWrongAttempt("Huge on land, but look up at the celestial sky!")
                    }
                )
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 2: FROG_CROSSING ("Help the frog jump across the pond!")
// ----------------------------------------------------------------------------
@Composable
fun FrogCrossingPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var cloudOffsetX by remember { mutableFloatStateOf(0f) }
    var lilypadOffsetX by remember { mutableFloatStateOf(0f) }
    var crocodileSleeping by remember { mutableStateOf(false) }
    var frogJumped by remember { mutableStateOf(false) }

    LaunchedEffect(cloudOffsetX) {
        if (abs(cloudOffsetX) > 90f) {
            crocodileSleeping = true
            soundManager.playSparkle()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Draggable Cloud at top
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset { IntOffset(cloudOffsetX.roundToInt(), 20) }
                .size(100.dp, 50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        cloudOffsetX += dragAmount.x
                    }
                }
                .testTag("draggable_cloud"),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = Color.White,
                    size = size,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(24.dp.toPx(), 24.dp.toPx())
                )
                drawRoundRect(
                    color = InkBlack,
                    size = size,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(24.dp.toPx(), 24.dp.toPx()),
                    style = Stroke(3.dp.toPx())
                )
            }
            Text("☁️ Drag Me", fontFamily = PatrickHandFontFamily, fontSize = 16.sp, color = InkBlack)
        }

        // River / Pond Scene
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pond water
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(PastelBlue, RoundedCornerShape(16.dp))
                    .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(16.dp))
            ) {
                // Crocodile in middle
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = if (crocodileSleeping) "🐊 Zzz..." else "🐊 CHOMP!",
                        fontFamily = PatrickHandFontFamily,
                        fontSize = 24.sp,
                        color = InkBlack
                    )
                }

                // Draggable Lilypad
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset { IntOffset(lilypadOffsetX.roundToInt() + 80, 50) }
                        .size(60.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                lilypadOffsetX += dragAmount.x
                            }
                        }
                        .testTag("draggable_lilypad"),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(color = PastelGreenDark, radius = size.width * 0.45f)
                        drawCircle(color = InkBlack, radius = size.width * 0.45f, style = Stroke(2.5.dp.toPx()))
                    }
                    Text("🍃", fontSize = 24.sp)
                }

                // Finish Shore Flag
                Text(
                    text = "🚩 Goal",
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 20.sp,
                    color = InkBlack,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                )

                // Frog on Left Shore
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .offset { IntOffset(if (frogJumped) 220 else 10, 0) }
                        .clickable {
                            if (crocodileSleeping || lilypadOffsetX > 80f) {
                                frogJumped = true
                                soundManager.playBoing()
                                soundManager.playVictory()
                                onSolved()
                            } else {
                                soundManager.playBuzzer()
                                onWrongAttempt("Snap! The crocodile is awake! Move the cloud or lilypad!")
                            }
                        }
                        .testTag("frog_tap_target")
                ) {
                    Text(text = "🐸 Hop!", fontFamily = PatrickHandFontFamily, fontSize = 28.sp)
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 3: LIGHTBULB_CIRCUIT ("Turn on the lightbulb!")
// ----------------------------------------------------------------------------
@Composable
fun LightbulbCircuitPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var paperclipOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    var circuitClosed by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Battery on Left, Lightbulb on Right
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Battery
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(54.dp, 80.dp)
                        .background(PastelOrange, RoundedCornerShape(8.dp))
                        .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🔋\n1.5V", fontFamily = PatrickHandFontFamily, fontSize = 18.sp, textAlign = TextAlign.Center)
                }
            }

            // Wire with broken gap
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Left wire
                    drawLine(color = FailRed, start = Offset(0f, size.height / 2f), end = Offset(size.width * 0.35f, size.height / 2f), strokeWidth = 4.dp.toPx(), cap = StrokeCap.Round)
                    // Right wire
                    drawLine(color = FailRed, start = Offset(size.width * 0.65f, size.height / 2f), end = Offset(size.width, size.height / 2f), strokeWidth = 4.dp.toPx(), cap = StrokeCap.Round)
                }
                if (circuitClosed) {
                    Text("⚡ CONNECTED!", fontFamily = PatrickHandFontFamily, fontSize = 12.sp, color = SuccessGreen)
                } else {
                    Text("✂️ GAP", fontFamily = PatrickHandFontFamily, fontSize = 13.sp, color = InkBlack)
                }
            }

            // Lightbulb
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(if (circuitClosed) LightbulbGlow else Color(0xFFEEEEEE), CircleShape)
                        .border(BorderStroke(3.dp, InkBlack), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (circuitClosed) "💡 GLOW!" else "💡 OFF", fontFamily = PatrickHandFontFamily, fontSize = 16.sp)
                }
            }
        }

        // Stationery items on desk
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Stationery on desk (Which conducts electricity?):", fontFamily = PatrickHandFontFamily, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Eraser (insulator)
                CartoonCardItem(title = "Eraser", emoji = "🧼", bg = PastelPink) {
                    soundManager.playBuzzer()
                    onWrongAttempt("Rubber is an electrical insulator! Won't conduct!")
                }

                // Pencil (wood)
                CartoonCardItem(title = "Wooden Stick", emoji = "🪵", bg = PastelOrange) {
                    soundManager.playBuzzer()
                    onWrongAttempt("Wood does not conduct electricity!")
                }

                // Metal Paperclip (Draggable conductor!)
                Box(
                    modifier = Modifier
                        .offset { IntOffset(paperclipOffset.x.roundToInt(), paperclipOffset.y.roundToInt()) }
                        .size(70.dp, 60.dp)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragEnd = {
                                    if (paperclipOffset.y < -120f) {
                                        circuitClosed = true
                                        soundManager.playSparkle()
                                        soundManager.playVictory()
                                        onSolved()
                                    } else {
                                        soundManager.playBoing()
                                        paperclipOffset = Offset(0f, 0f)
                                    }
                                }
                            ) { change, dragAmount ->
                                change.consume()
                                paperclipOffset += dragAmount
                            }
                        }
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                        .border(BorderStroke(2.5.dp, InkBlack), RoundedCornerShape(12.dp))
                        .testTag("draggable_paperclip"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📎", fontSize = 24.sp)
                        Text("Paperclip", fontFamily = PatrickHandFontFamily, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 4: MATCHSTICK_MATH ("5 + 7 = 2")
// ----------------------------------------------------------------------------
@Composable
fun MatchstickMathPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var matchstickMoved by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (matchstickMoved) "-5 + 7 = 2" else " 5 + 7 = 2",
                fontFamily = PatrickHandFontFamily,
                fontSize = 44.sp,
                color = if (matchstickMoved) SuccessGreen else InkBlack,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (!matchstickMoved) {
                CartoonButton(
                    text = "Move vertical stick from '+' to front of 5",
                    backgroundColor = PastelYellow,
                    onClick = {
                        matchstickMoved = true
                        soundManager.playClick()
                        soundManager.playVictory()
                        onSolved()
                    },
                    testTag = "move_matchstick_btn"
                )
            } else {
                Text(
                    text = "True! -5 + 7 = 2! Brilliant!",
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 22.sp,
                    color = SuccessGreen
                )
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 5: SLEEPY_STUDENT ("Wake up the sleepy student!")
// ----------------------------------------------------------------------------
@Composable
fun SleepyStudentPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var curtainsOpen by remember { mutableStateOf(false) }
    var alarmTapped by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Window with Curtains
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
                .size(160.dp, 120.dp)
                .background(if (curtainsOpen) Color(0xFFFFF9C4) else Color(0xFF90A4AE), RoundedCornerShape(12.dp))
                .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(12.dp))
                .clickable {
                    curtainsOpen = true
                    soundManager.playPop()
                    soundManager.playVictory()
                    onSolved()
                }
                .testTag("curtains_window"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (curtainsOpen) "☀️ SUNSHINE!" else "🪟 Swipe Curtains",
                fontFamily = PatrickHandFontFamily,
                fontSize = 18.sp,
                color = InkBlack
            )
        }

        // Student at desk
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (curtainsOpen) "😳 WHOA! I'M AWAKE!" else "😴 Zzz... Zzz...",
                fontFamily = PatrickHandFontFamily,
                fontSize = 28.sp,
                color = InkBlack
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text("👨‍🎓 Student", fontSize = 42.sp)
        }

        // Alarm clock
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {
            CartoonButton(
                text = "⏰ Ring Alarm ($alarmTapped)",
                backgroundColor = PastelOrange,
                onClick = {
                    alarmTapped++
                    soundManager.playBoing()
                    if (alarmTapped >= 3) {
                        curtainsOpen = true
                        soundManager.playVictory()
                        onSolved()
                    } else {
                        onWrongAttempt("He turned off the alarm and went back to sleep! Try opening the curtains!")
                    }
                }
            )
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 6: ELEPHANT_FRIDGE ("Put the huge elephant into the fridge!")
// ----------------------------------------------------------------------------
@Composable
fun ElephantFridgePuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var fridgeOpen by remember { mutableStateOf(false) }
    var elephantScale by remember { mutableFloatStateOf(1f) }
    var elephantInside by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Elephant (shrinkable by tapping)
            if (!elephantInside) {
                Box(
                    modifier = Modifier
                        .scale(elephantScale)
                        .clickable {
                            if (elephantScale > 0.45f) {
                                elephantScale -= 0.25f
                                soundManager.playPop()
                            }
                        }
                        .testTag("elephant_target")
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🐘", fontSize = if (elephantScale < 0.6f) 36.sp else 64.sp)
                        Text(
                            if (elephantScale < 0.6f) "Pocket Elephant!" else "Tap to shrink!",
                            fontFamily = PatrickHandFontFamily,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Fridge
            Box(
                modifier = Modifier
                    .size(130.dp, 200.dp)
                    .background(Color(0xFFE0F7FA), RoundedCornerShape(14.dp))
                    .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(14.dp))
                    .clickable {
                        fridgeOpen = !fridgeOpen
                        soundManager.playClick()
                    }
                    .testTag("fridge_target"),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(if (fridgeOpen) "🚪 OPEN" else "🚪 CLOSED", fontFamily = PatrickHandFontFamily, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    if (elephantInside) {
                        Text("🐘 Inside!", fontFamily = PatrickHandFontFamily, fontSize = 20.sp, color = SuccessGreen)
                    } else if (fridgeOpen) {
                        CartoonButton(
                            text = "Put In ➔",
                            backgroundColor = PastelGreen,
                            onClick = {
                                if (elephantScale < 0.6f) {
                                    elephantInside = true
                                    soundManager.playVictory()
                                    onSolved()
                                } else {
                                    soundManager.playBuzzer()
                                    onWrongAttempt("Elephant is too huge! Tap the elephant to shrink it first!")
                                }
                            }
                        )
                    } else {
                        Text("Tap to open door", fontFamily = PatrickHandFontFamily, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 7: BALLOON_CACTUS ("Save the red balloon from popping!")
// ----------------------------------------------------------------------------
@Composable
fun BalloonCactusPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var corkPlaced by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Ceiling Spikes
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (corkPlaced) "🛡️ SPIKES CAPPED & SAFE!" else "🌵🌵🌵 SHARP SPIKES 🌵🌵🌵",
                fontFamily = PatrickHandFontFamily,
                fontSize = 20.sp,
                color = if (corkPlaced) SuccessGreen else FailRed
            )
        }

        // Floating Balloon in middle
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("🎈", fontSize = 68.sp)
            Text("Floating Up!", fontFamily = PatrickHandFontFamily, fontSize = 16.sp)
        }

        // Protective rubber cork
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {
            CartoonButton(
                text = "Cover Spikes with Rubber Cork 🛡️",
                backgroundColor = PastelGreen,
                onClick = {
                    corkPlaced = true
                    soundManager.playPop()
                    soundManager.playVictory()
                    onSolved()
                },
                testTag = "cap_spikes_button"
            )
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 8: HIDDEN_KEY ("Find the hidden key!")
// ----------------------------------------------------------------------------
@Composable
fun HiddenKeyPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var plantMoved by remember { mutableStateOf(false) }
    var keyUnlocked by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Treasure Chest at center
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(if (keyUnlocked) "📦✨ OPEN!" else "📦 LOCKED CHEST", fontSize = 52.sp)
            if (keyUnlocked) {
                Text("💎 Gemstones Discovered!", fontFamily = PatrickHandFontFamily, fontSize = 18.sp, color = SuccessGreen)
            }
        }

        // Potted Plant (moving reveals key)
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp, bottom = 40.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "🪴",
                    fontSize = 54.sp,
                    modifier = Modifier
                        .offset { IntOffset(if (plantMoved) 70 else 0, 0) }
                        .clickable {
                            plantMoved = true
                            soundManager.playPop()
                        }
                        .testTag("potted_plant")
                )
                Text(if (plantMoved) "Moved Aside!" else "Tap to move plant", fontFamily = PatrickHandFontFamily, fontSize = 14.sp)
            }
        }

        // Revealed Key
        if (plantMoved && !keyUnlocked) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 40.dp, bottom = 45.dp)
            ) {
                CartoonButton(
                    text = "🗝️ Use Key!",
                    backgroundColor = GoldStar,
                    onClick = {
                        keyUnlocked = true
                        soundManager.playVictory()
                        onSolved()
                    },
                    testTag = "use_key_button"
                )
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 9: SHAVE_GRANDPA ("Make grandpa look 30 years younger!")
// ----------------------------------------------------------------------------
@Composable
fun ShaveGrandpaPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var beardRubs by remember { mutableIntStateOf(0) }
    val solved = beardRubs >= 5

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (solved) "👨‍🦱" else "🧔‍♂️",
                fontSize = 72.sp,
                modifier = Modifier
                    .clickable {
                        beardRubs++
                        soundManager.playScribble()
                        if (beardRubs >= 5) {
                            soundManager.playVictory()
                            onSolved()
                        }
                    }
                    .testTag("grandpa_beard")
            )

            Text(
                text = if (solved) "30 Years Younger!" else "Beard thickness: ${5 - beardRubs} layers left",
                fontFamily = PatrickHandFontFamily,
                fontSize = 18.sp,
                color = if (solved) SuccessGreen else InkBlack
            )

            Spacer(modifier = Modifier.height(20.dp))

            CartoonButton(
                text = "🧼 Rub Eraser on Beard",
                backgroundColor = PastelPink,
                onClick = {
                    beardRubs++
                    soundManager.playScribble()
                    if (beardRubs >= 5) {
                        soundManager.playVictory()
                        onSolved()
                    }
                },
                testTag = "rub_eraser_button"
            )
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 10: HIGHEST_NUMBER ("Which number is the HIGHEST?")
// ----------------------------------------------------------------------------
@Composable
fun HighestNumberPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // "7" physically at the very top of the screen!
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
        ) {
            CartoonCardItem(title = "7", emoji = "7️⃣", bg = PastelYellow) {
                soundManager.playVictory()
                onSolved()
            }
        }

        // Lower numbers on screen
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CartoonCardItem(title = "15", emoji = "🔢", bg = PastelBlue) {
                soundManager.playBuzzer()
                onWrongAttempt("15 is low on the page!")
            }

            CartoonCardItem(title = "80", emoji = "🔢", bg = PastelGreen) {
                soundManager.playBuzzer()
                onWrongAttempt("Look at physical vertical altitude on the notebook!")
            }

            CartoonCardItem(title = "200", emoji = "🔢", bg = PastelRed) {
                soundManager.playBuzzer()
                onWrongAttempt("Numerically high, but physically lower than 7 up top!")
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 11: GIRAFFE_NECK ("Feed the hungry giraffe!")
// ----------------------------------------------------------------------------
@Composable
fun GiraffeNeckPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var neckLength by remember { mutableFloatStateOf(40f) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Tree leaves at top
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 20.dp, end = 30.dp)
        ) {
            Text("🌳 Delicious Leaves!", fontFamily = PatrickHandFontFamily, fontSize = 20.sp, color = SuccessGreen)
        }

        // Stretchy Giraffe
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 40.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Giraffe head (draggable upwards)
            Box(
                modifier = Modifier
                    .offset { IntOffset(0, -neckLength.roundToInt()) }
                    .size(60.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            if (dragAmount.y < 0) {
                                neckLength += abs(dragAmount.y)
                                if (neckLength >= 140f) {
                                    soundManager.playBoing()
                                    soundManager.playVictory()
                                    onSolved()
                                }
                            }
                        }
                    }
                    .testTag("giraffe_head"),
                contentAlignment = Alignment.Center
            ) {
                Text("🦒", fontSize = 48.sp)
            }

            // Stretchy cartoon neck
            Box(
                modifier = Modifier
                    .width(18.dp)
                    .height(neckLength.dp)
                    .background(PastelYellowDark, RoundedCornerShape(8.dp))
                    .border(BorderStroke(2.dp, InkBlack), RoundedCornerShape(8.dp))
            )

            Text("🦒 Body", fontSize = 36.sp)
            Text("Drag head up to stretch neck!", fontFamily = PatrickHandFontFamily, fontSize = 14.sp)
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 12: HATCH_CHICK ("Help the chick hatch!")
// ----------------------------------------------------------------------------
@Composable
fun HatchChickPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var blanketCovered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (blanketCovered) "🐥 Chirp! Hatched!" else "🥚 (Shivering Cold)",
                fontSize = 58.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            CartoonButton(
                text = if (blanketCovered) "Warm & Cozy!" else "Wrap in Warm Blanket 🧣",
                backgroundColor = PastelYellow,
                onClick = {
                    blanketCovered = true
                    soundManager.playSparkle()
                    soundManager.playVictory()
                    onSolved()
                },
                testTag = "wrap_blanket_button"
            )
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 13: PIPE_CONNECT ("Fix the pipe leak!")
// ----------------------------------------------------------------------------
@Composable
fun PipeConnectPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var pipe1Rot by remember { mutableFloatStateOf(90f) }
    var pipe2Rot by remember { mutableFloatStateOf(180f) }
    val solved = (pipe1Rot % 360 == 0f) && (pipe2Rot % 360 == 0f)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("🚰 Water Faucet", fontFamily = PatrickHandFontFamily, fontSize = 24.sp)

            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Box(
                    modifier = Modifier
                        .rotate(pipe1Rot)
                        .size(70.dp)
                        .background(PastelBlue, RoundedCornerShape(10.dp))
                        .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(10.dp))
                        .clickable {
                            pipe1Rot += 90f
                            soundManager.playClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("║", fontSize = 36.sp, color = InkBlack)
                }

                Box(
                    modifier = Modifier
                        .rotate(pipe2Rot)
                        .size(70.dp)
                        .background(PastelBlue, RoundedCornerShape(10.dp))
                        .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(10.dp))
                        .clickable {
                            pipe2Rot += 90f
                            soundManager.playClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("║", fontSize = 36.sp, color = InkBlack)
                }
            }

            Text(if (solved) "🌻 Sunflower Blooming!" else "🥀 Withered Sunflower", fontSize = 32.sp)

            CartoonButton(
                text = "Turn On Faucet 🚰",
                backgroundColor = PastelGreen,
                onClick = {
                    if (solved) {
                        soundManager.playVictory()
                        onSolved()
                    } else {
                        soundManager.playBuzzer()
                        onWrongAttempt("Water is leaking! Tap the pipes to align them straight!")
                    }
                }
            )
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 14: BALANCE_SEESAW ("Balance the seesaw!")
// ----------------------------------------------------------------------------
@Composable
fun BalanceSeesawPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var hippoRemoved by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (hippoRemoved) "⚖️ Balanced!" else "🦛 ─── 🐦 (Tilted Down!)",
                fontFamily = PatrickHandFontFamily,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (!hippoRemoved) {
                CartoonButton(
                    text = "Drag Hippo Off the Seesaw 🦛",
                    backgroundColor = PastelOrange,
                    onClick = {
                        hippoRemoved = true
                        soundManager.playBoing()
                        soundManager.playVictory()
                        onSolved()
                    }
                )
            } else {
                Text("Seesaw is in perfect equilibrium!", fontFamily = PatrickHandFontFamily, fontSize = 20.sp, color = SuccessGreen)
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 15: ODD_OWL_OUT ("Find the imposter!")
// ----------------------------------------------------------------------------
@Composable
fun OddOwlPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..4) {
            CartoonCardItem(title = "Owl $i", emoji = "🦉", bg = PastelYellow) {
                soundManager.playBuzzer()
                onWrongAttempt("Just a regular wise owl! Hoot hoot!")
            }
        }

        // Imposter cat in owl costume!
        CartoonCardItem(title = "Owl 5", emoji = "🐱", bg = PastelPink) {
            soundManager.playVictory()
            onSolved()
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 16: FLASHLIGHT_DARK ("Light up the dark forest!")
// ----------------------------------------------------------------------------
@Composable
fun FlashlightDarkPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var battery1Inserted by remember { mutableStateOf(false) }
    var battery2Inserted by remember { mutableStateOf(false) }
    val ready = battery1Inserted && battery2Inserted

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(if (ready) "🌲🌲🌲 Path Illuminated! 💡" else "🌲🌲🌲 (Pitch Black Spooky Forest) 🌲🌲🌲", fontFamily = PatrickHandFontFamily, fontSize = 20.sp)

            Text("🔦 Flashlight: ${if (ready) "Fully Charged!" else "${(if (battery1Inserted) 1 else 0) + (if (battery2Inserted) 1 else 0)}/2 Batteries"}", fontSize = 20.sp)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                if (!battery1Inserted) {
                    CartoonButton(text = "Insert Battery 1 🔋", backgroundColor = PastelGreen, onClick = {
                        battery1Inserted = true
                        soundManager.playPop()
                    })
                }
                if (!battery2Inserted) {
                    CartoonButton(text = "Insert Battery 2 🔋", backgroundColor = PastelGreen, onClick = {
                        battery2Inserted = true
                        soundManager.playPop()
                    })
                }
            }

            if (ready) {
                CartoonButton(text = "Turn On Flashlight! ✨", backgroundColor = LightbulbGlow, onClick = {
                    soundManager.playVictory()
                    onSolved()
                })
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 17: DONUT_WHEEL ("Help the racecar reach the finish line!")
// ----------------------------------------------------------------------------
@Composable
fun DonutWheelPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var donutMounted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(if (donutMounted) "🏎️🍩 Vroooom! Finish Line!" else "🏎️ (Missing back wheel!)", fontSize = 36.sp)

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CartoonCardItem(title = "Square Brick", emoji = "🧱", bg = PastelOrange) {
                    soundManager.playBuzzer()
                    onWrongAttempt("Square wheels don't roll!")
                }

                CartoonCardItem(title = "Triangle", emoji = "📐", bg = PastelBlue) {
                    soundManager.playBuzzer()
                    onWrongAttempt("Triangles are too pointy to roll!")
                }

                CartoonCardItem(title = "Chocolate Donut", emoji = "🍩", bg = PastelPink) {
                    donutMounted = true
                    soundManager.playBoing()
                    soundManager.playVictory()
                    onSolved()
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 18: PRISON_ESCAPE ("Escape without waking the guard!")
// ----------------------------------------------------------------------------
@Composable
fun PrisonEscapePuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var keyStolen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("👮💤 Guard is snoring peacefully...", fontFamily = PatrickHandFontFamily, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(16.dp))

            if (!keyStolen) {
                CartoonButton(
                    text = "Gently Steal Key From Belt 🗝️",
                    backgroundColor = GoldStar,
                    onClick = {
                        keyStolen = true
                        soundManager.playSparkle()
                        soundManager.playVictory()
                        onSolved()
                    }
                )
            } else {
                Text("Unlocked prison cell! Freedom!", fontFamily = PatrickHandFontFamily, fontSize = 24.sp, color = SuccessGreen)
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 19: CATCH_APPLES ("Catch 5 falling apples!")
// ----------------------------------------------------------------------------
@Composable
fun CatchApplesPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var caughtCount by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("🍎 Apples Caught: $caughtCount/5", fontFamily = PatrickHandFontFamily, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))

            if (caughtCount < 5) {
                CartoonButton(
                    text = "Catch Falling Apple 🧺",
                    backgroundColor = PastelGreen,
                    onClick = {
                        caughtCount++
                        soundManager.playPop()
                        if (caughtCount >= 5) {
                            soundManager.playVictory()
                            onSolved()
                        }
                    }
                )
            } else {
                Text("Basket is full of delicious apples!", fontFamily = PatrickHandFontFamily, fontSize = 22.sp, color = SuccessGreen)
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 20: TSHIRT_HOLES ("How many holes does this t-shirt have?")
// ----------------------------------------------------------------------------
@Composable
fun TShirtHolesPuzzle(
    level: PuzzleLevel,
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("👕", fontSize = 80.sp)
        Text("Neck, sleeves, waist, and holes through both sides!", fontFamily = PatrickHandFontFamily, fontSize = 16.sp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val options = level.extraOptions.ifEmpty { listOf("4", "6", "8", "10") }
            options.forEachIndexed { index, option ->
                CartoonButton(
                    text = option,
                    backgroundColor = PastelYellow,
                    onClick = {
                        if (index == level.correctIndex) {
                            soundManager.playVictory()
                            onSolved()
                        } else {
                            soundManager.playBuzzer()
                            onWrongAttempt("Wrong count! Don't forget the openings and front/back edges!")
                        }
                    }
                )
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 21: WELL_KITTEN ("Rescue the kitten stuck in the well!")
// ----------------------------------------------------------------------------
@Composable
fun WellKittenPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var rescued by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(if (rescued) "🐱 Purrr! Kitten is Safe!" else "🪣 Stone Well: 🐱 (Meow!)", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))

            if (!rescued) {
                CartoonButton(
                    text = "Lower Bucket on Rope 🪢",
                    backgroundColor = PastelBlue,
                    onClick = {
                        rescued = true
                        soundManager.playBoing()
                        soundManager.playVictory()
                        onSolved()
                    }
                )
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 22: TRAFFIC_LIGHT ("Turn the traffic light green!")
// ----------------------------------------------------------------------------
@Composable
fun TrafficLightPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var hasYellow by remember { mutableStateOf(false) }
    var hasBlue by remember { mutableStateOf(false) }
    val isGreen = hasYellow && hasBlue

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(if (isGreen) "🟢 GREEN! Cars Vroom Away!" else "🔴 RED LIGHT (Traffic Jam!)", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CartoonButton(
                    text = "Add Yellow Paint 🟡",
                    backgroundColor = PastelYellow,
                    onClick = {
                        hasYellow = true
                        soundManager.playPop()
                        if (hasYellow && hasBlue) {
                            soundManager.playVictory()
                            onSolved()
                        }
                    }
                )

                CartoonButton(
                    text = "Add Blue Paint 🔵",
                    backgroundColor = PastelBlue,
                    onClick = {
                        hasBlue = true
                        soundManager.playPop()
                        if (hasYellow && hasBlue) {
                            soundManager.playVictory()
                            onSolved()
                        }
                    }
                )
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 23: FIND_STARS ("Find all 5 hidden doodle stars!")
// ----------------------------------------------------------------------------
@Composable
fun FindStarsPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    val foundStars = remember { mutableStateListOf<Int>() }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Found Stars: ${foundStars.size}/5",
            fontFamily = PatrickHandFontFamily,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
        )

        val starPositions = listOf(
            Offset(0.2f, 0.25f),
            Offset(0.75f, 0.3f),
            Offset(0.5f, 0.5f),
            Offset(0.25f, 0.7f),
            Offset(0.8f, 0.75f)
        )

        starPositions.forEachIndexed { index, pos ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = (pos.x * 260).dp, y = (pos.y * 360).dp)
                        .size(44.dp)
                        .clickable {
                            if (!foundStars.contains(index)) {
                                foundStars.add(index)
                                soundManager.playPop()
                                if (foundStars.size == 5) {
                                    soundManager.playVictory()
                                    onSolved()
                                }
                            }
                        }
                ) {
                    CartoonStar(filled = foundStars.contains(index), size = 36.dp)
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 24: MONSTER_BED ("Scare away the monster under the bed!")
// ----------------------------------------------------------------------------
@Composable
fun MonsterBedPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    var lampOn by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(if (lampOn) "🛏️ Just Fuzzy Bunny Slippers! 🐰" else "🛏️👀 Spooky Eyes Under Bed!", fontSize = 28.sp)
            Spacer(modifier = Modifier.height(20.dp))

            CartoonButton(
                text = "Pull Bedside Lamp Cord 💡",
                backgroundColor = LightbulbGlow,
                onClick = {
                    lampOn = true
                    soundManager.playClick()
                    soundManager.playVictory()
                    onSolved()
                }
            )
        }
    }
}

// ----------------------------------------------------------------------------
// LEVEL 25: VAULT_UNLOCKED ("Unlock the Master Brain Vault!")
// ----------------------------------------------------------------------------
@Composable
fun VaultUnlockedPuzzle(
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    val symbols = listOf("🧠 Brain", "💡 Lightbulb", "⭐ Star", "🔑 Key")
    var dial1 by remember { mutableIntStateOf(0) }
    var dial2 by remember { mutableIntStateOf(0) }
    var dial3 by remember { mutableIntStateOf(0) }

    // Solution: Think (Brain = 0), Shine (Lightbulb = 1), Wonder (Star = 2)
    val solved = dial1 == 0 && dial2 == 1 && dial3 == 2

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("🔐 VAULT CODE", fontFamily = PatrickHandFontFamily, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CartoonButton(text = symbols[dial1], backgroundColor = PastelYellow, onClick = {
                    dial1 = (dial1 + 1) % symbols.size
                    soundManager.playClick()
                })
                CartoonButton(text = symbols[dial2], backgroundColor = PastelYellow, onClick = {
                    dial2 = (dial2 + 1) % symbols.size
                    soundManager.playClick()
                })
                CartoonButton(text = symbols[dial3], backgroundColor = PastelYellow, onClick = {
                    dial3 = (dial3 + 1) % symbols.size
                    soundManager.playClick()
                })
            }

            Spacer(modifier = Modifier.height(24.dp))

            CartoonButton(
                text = "Pull Golden Vault Handle 🏆",
                backgroundColor = GoldStar,
                onClick = {
                    if (solved) {
                        soundManager.playVictory()
                        onSolved()
                    } else {
                        soundManager.playBuzzer()
                        onWrongAttempt("Vault didn't open! Remember the clue: Think, Shine, Wonder!")
                    }
                }
            )
        }
    }
}

// ----------------------------------------------------------------------------
// GENERATED_TRICK (For procedural / daily puzzles)
// ----------------------------------------------------------------------------
@Composable
fun GeneratedTrickPuzzle(
    level: PuzzleLevel,
    soundManager: SoundManager,
    onSolved: () -> Unit,
    onWrongAttempt: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        level.extraOptions.forEachIndexed { index, option ->
            CartoonButton(
                text = option,
                backgroundColor = if (index % 2 == 0) PastelYellow else PastelBlue,
                modifier = Modifier.fillMaxWidth(0.9f),
                onClick = {
                    if (index == level.correctIndex) {
                        soundManager.playVictory()
                        onSolved()
                    } else {
                        soundManager.playBuzzer()
                        onWrongAttempt("Think outside the box! Try another choice!")
                    }
                }
            )
        }
    }
}

// ----------------------------------------------------------------------------
// Helper Card item
// ----------------------------------------------------------------------------
@Composable
fun CartoonCardItem(
    title: String,
    emoji: String,
    bg: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(86.dp)
            .background(bg, RoundedCornerShape(14.dp))
            .border(BorderStroke(2.5.dp, InkBlack), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("item_$title"),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 34.sp)
            Text(title, fontFamily = PatrickHandFontFamily, fontSize = 13.sp, color = InkBlack)
        }
    }
}
