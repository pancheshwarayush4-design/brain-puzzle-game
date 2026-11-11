package com.example.ui.components

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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldStar
import com.example.ui.theme.InkBlack
import com.example.ui.theme.LightbulbGlow
import com.example.ui.theme.PastelYellow
import com.example.ui.theme.PatrickHandFontFamily
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Hand-drawn comic style button with thick black outline, rounded irregular corners,
 * and a 3D comic shadow offset.
 */
@Composable
fun CartoonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = PastelYellow,
    contentColor: Color = InkBlack,
    icon: (@Composable () -> Unit)? = null,
    testTag: String = "cartoon_button"
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val shadowOffset = if (isPressed) 1.dp else 4.dp
    val scale = if (isPressed) 0.97f else 1f

    Box(
        modifier = modifier
            .scale(scale)
            .padding(bottom = 4.dp, end = 4.dp)
            .testTag(testTag)
    ) {
        // Comic drop-shadow
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffset, y = shadowOffset)
                .background(InkBlack, RoundedCornerShape(14.dp))
        )
        // Main button face
        Box(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(14.dp))
                .border(BorderStroke(2.5.dp, InkBlack), RoundedCornerShape(14.dp))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    icon()
                    Spacer(modifier = Modifier.width(6.dp))
                }
                Text(
                    text = text,
                    fontFamily = PatrickHandFontFamily,
                    fontSize = 18.sp,
                    color = contentColor
                )
            }
        }
    }
}

/**
 * Cartoon circle action button (for icons like hints, settings, reset)
 */
@Composable
fun CartoonIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    size: Dp = 48.dp,
    testTag: String = "cartoon_icon_button",
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val shadowOffset = if (isPressed) 1.dp else 3.5.dp
    val scale = if (isPressed) 0.94f else 1f

    Box(
        modifier = modifier
            .size(size + 4.dp)
            .scale(scale)
            .testTag(testTag)
    ) {
        // Shadow
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = shadowOffset, y = shadowOffset)
                .background(InkBlack, RoundedCornerShape(percent = 50))
        )
        // Face
        Box(
            modifier = Modifier
                .size(size)
                .background(backgroundColor, RoundedCornerShape(percent = 50))
                .border(BorderStroke(2.5.dp, InkBlack), RoundedCornerShape(percent = 50))
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

/**
 * Hand-drawn cartoon lightbulb with glowing rays
 */
@Composable
fun CartoonLightbulb(
    modifier: Modifier = Modifier,
    glow: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bulb_glow")
    val rayAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ray_alpha"
    )

    Canvas(modifier = modifier.size(36.dp)) {
        val w = size.width
        val h = size.height

        // Glowing rays
        if (glow) {
            val rayLength = 6.dp.toPx()
            val center = Offset(w * 0.5f, h * 0.42f)
            val rayAngles = listOf(-90f, -45f, -135f, 0f, 180f, 45f, 135f)
            for (angle in rayAngles) {
                val rad = Math.toRadians(angle.toDouble())
                val r1 = w * 0.42f
                val r2 = r1 + rayLength
                val p1 = Offset(center.x + (r1 * cos(rad)).toFloat(), center.y + (r1 * sin(rad)).toFloat())
                val p2 = Offset(center.x + (r2 * cos(rad)).toFloat(), center.y + (r2 * sin(rad)).toFloat())
                drawLine(
                    color = LightbulbGlow.copy(alpha = rayAlpha),
                    start = p1,
                    end = p2,
                    strokeWidth = 2.5.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        // Glass bulb
        val bulbPath = Path().apply {
            moveTo(w * 0.32f, h * 0.68f)
            cubicTo(w * 0.1f, h * 0.5f, w * 0.1f, h * 0.15f, w * 0.5f, h * 0.15f)
            cubicTo(w * 0.9f, h * 0.15f, w * 0.9f, h * 0.5f, w * 0.68f, h * 0.68f)
            close()
        }
        drawPath(
            path = bulbPath,
            color = if (glow) LightbulbGlow else Color(0xFFFFF9C4)
        )
        drawPath(
            path = bulbPath,
            color = InkBlack,
            style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Filament inside
        val filamentPath = Path().apply {
            moveTo(w * 0.42f, h * 0.65f)
            lineTo(w * 0.45f, h * 0.42f)
            lineTo(w * 0.55f, h * 0.42f)
            lineTo(w * 0.58f, h * 0.65f)
        }
        drawPath(
            path = filamentPath,
            color = InkBlack,
            style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
        )

        // Screw base
        drawRect(
            color = Color(0xFFCFD8DC),
            topLeft = Offset(w * 0.35f, h * 0.68f),
            size = Size(w * 0.3f, h * 0.16f)
        )
        drawRect(
            color = InkBlack,
            topLeft = Offset(w * 0.35f, h * 0.68f),
            size = Size(w * 0.3f, h * 0.16f),
            style = Stroke(width = 2.dp.toPx())
        )
        // Thread ridges
        drawLine(
            color = InkBlack,
            start = Offset(w * 0.35f, h * 0.74f),
            end = Offset(w * 0.65f, h * 0.74f),
            strokeWidth = 1.5.dp.toPx()
        )
    }
}

/**
 * Hand-drawn cartoon Star
 */
@Composable
fun CartoonStar(
    filled: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    color: Color = GoldStar
) {
    Canvas(modifier = modifier.size(size)) {
        drawDoodleStar(
            center = Offset(this.size.width / 2f, this.size.height / 2f),
            radius = this.size.width * 0.46f,
            filled = filled,
            fillColor = color
        )
    }
}

fun DrawScope.drawDoodleStar(
    center: Offset,
    radius: Float,
    filled: Boolean,
    fillColor: Color = GoldStar
) {
    val points = 5
    val innerRadius = radius * 0.42f
    val path = Path()

    for (i in 0 until points * 2) {
        val r = if (i % 2 == 0) radius else innerRadius
        val angle = (i * PI / points) - (PI / 2)
        val x = center.x + (r * cos(angle)).toFloat()
        val y = center.y + (r * sin(angle)).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()

    if (filled) {
        drawPath(path = path, color = fillColor, style = Fill)
    }
    drawPath(
        path = path,
        color = InkBlack,
        style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}
