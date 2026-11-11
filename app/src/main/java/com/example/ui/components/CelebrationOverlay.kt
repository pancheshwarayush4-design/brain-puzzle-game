package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FailRed
import com.example.ui.theme.GoldStar
import com.example.ui.theme.InkBlack
import com.example.ui.theme.PastelBlue
import com.example.ui.theme.PastelGreen
import com.example.ui.theme.PastelRed
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.PatrickHandFontFamily
import com.example.ui.theme.SuccessGreen
import kotlin.math.cos
import kotlin.math.sin

/**
 * Green checkmark celebration popup when a puzzle is solved!
 */
@Composable
fun VictoryCelebrationModal(
    levelNumber: Int,
    stars: Int,
    explanation: String,
    onNextLevel: () -> Unit,
    onReplay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val stampScale = remember { Animatable(0f) }
    val cardScale = remember { Animatable(0.6f) }
    val cardAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Rubber stamp slam animation
        stampScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        cardAlpha.animateTo(1f, tween(300))
        cardScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x88000000))
            .padding(24.dp)
            .testTag("victory_modal"),
        contentAlignment = Alignment.Center
    ) {
        // Floating Confetti Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val colors = listOf(PastelYellow, PastelGreen, PastelBlue, PastelRed, GoldStar)
            val center = Offset(size.width / 2f, size.height / 2f)
            for (i in 0..28) {
                val angle = (i * 13.5).toFloat()
                val dist = (size.width * 0.2f) + (i % 6) * 45f
                val cx = center.x + (dist * cos(angle)).toFloat()
                val cy = center.y + (dist * sin(angle)).toFloat() - 40f
                drawCircle(
                    color = colors[i % colors.size],
                    radius = (5 + (i % 4) * 2).dp.toPx(),
                    center = Offset(cx, cy)
                )
            }
        }

        // Victory Card styled as a pinned notebook paper note
        Column(
            modifier = Modifier
                .scale(cardScale.value)
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(18.dp))
                .background(Color(0xFFFFFDF8), RoundedCornerShape(18.dp))
                .border(BorderStroke(3.dp, InkBlack), RoundedCornerShape(18.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Big Green Checkmark Stamp
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .scale(stampScale.value)
                    .rotate(-8f)
                    .background(Color(0xFFE8F5E9), CircleShape)
                    .border(BorderStroke(4.dp, SuccessGreen), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(48.dp)) {
                    val w = size.width
                    val h = size.height
                    val checkPath = Path().apply {
                        moveTo(w * 0.2f, h * 0.52f)
                        lineTo(w * 0.42f, h * 0.74f)
                        lineTo(w * 0.82f, h * 0.26f)
                    }
                    drawPath(
                        path = checkPath,
                        color = SuccessGreen,
                        style = Stroke(
                            width = 6.dp.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "GENIUS!",
                fontFamily = PatrickHandFontFamily,
                fontSize = 32.sp,
                color = SuccessGreen
            )

            Text(
                text = "Level $levelNumber Solved",
                fontFamily = PatrickHandFontFamily,
                fontSize = 20.sp,
                color = InkBlack
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Stars Row
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (s in 1..3) {
                    CartoonStar(
                        filled = s <= stars,
                        size = 36.dp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Humorous Explanation Note
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F8E9), RoundedCornerShape(12.dp))
                    .border(BorderStroke(1.5.dp, Color(0xFFC5E1A5)), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = explanation,
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 17.sp,
                    color = InkBlack,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CartoonButton(
                    text = "Replay",
                    backgroundColor = Color(0xFFE0E0E0),
                    onClick = onReplay,
                    testTag = "replay_button"
                )

                CartoonButton(
                    text = "Next Level ➔",
                    backgroundColor = PastelGreen,
                    onClick = onNextLevel,
                    testTag = "next_level_button"
                )
            }
        }
    }
}

/**
 * Animated red cross ("X") stamp when an incorrect attempt is made
 */
@Composable
fun WrongAttemptOverlay(
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(100)) + scaleIn(spring(Spring.DampingRatioHighBouncy)),
        exit = fadeOut(tween(300)) + scaleOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .rotate(6f)
                    .background(Color(0xFFFFEBEE), CircleShape)
                    .border(BorderStroke(4.dp, FailRed), CircleShape)
                    .shadow(12.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(50.dp)) {
                    val w = size.width
                    val h = size.height
                    drawLine(
                        color = FailRed,
                        start = Offset(w * 0.22f, h * 0.22f),
                        end = Offset(w * 0.78f, h * 0.78f),
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = FailRed,
                        start = Offset(w * 0.78f, h * 0.22f),
                        end = Offset(w * 0.22f, h * 0.78f),
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}
