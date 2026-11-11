package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.PaperBackground
import com.example.ui.theme.PaperHole
import com.example.ui.theme.PaperHoleRing
import com.example.ui.theme.PaperMarginLine
import com.example.ui.theme.PaperRuledLine
import com.example.ui.theme.WoodDeskAccent
import com.example.ui.theme.WoodDeskDark
import com.example.ui.theme.WoodDeskGrain
import com.example.ui.theme.WoodDeskMedium

/**
 * Renders the wooden desk border around a ruled notebook paper sheet with spiral binding.
 */
@Composable
fun NotebookScaffold(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WoodDeskMedium)
    ) {
        // Wooden desk texture with subtle plank grain lines
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Planks vertical lines
            val plankWidth = 140.dp.toPx()
            var x = 0f
            while (x < w) {
                drawLine(
                    color = WoodDeskDark,
                    start = Offset(x, 0f),
                    end = Offset(x, h),
                    strokeWidth = 3f
                )
                // Grain lines inside plank
                drawLine(
                    color = WoodDeskGrain,
                    start = Offset(x + plankWidth * 0.35f, 0f),
                    end = Offset(x + plankWidth * 0.35f, h),
                    strokeWidth = 1.2f
                )
                drawLine(
                    color = WoodDeskAccent.copy(alpha = 0.3f),
                    start = Offset(x + plankWidth * 0.7f, 0f),
                    end = Offset(x + plankWidth * 0.7f, h),
                    strokeWidth = 1f
                )
                x += plankWidth
            }
        }

        // The Ruled Notebook Sheet placed in center with 10dp wooden desk reveal margin
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 12.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp), spotColor = Color(0x66000000))
                .clip(RoundedCornerShape(12.dp))
                .background(PaperBackground)
        ) {
            // Notebook ruling lines and red margin line + binder holes
            Canvas(modifier = Modifier.fillMaxSize()) {
                val sheetWidth = size.width
                val sheetHeight = size.height

                // Horizontal blue ruling lines
                val lineSpacing = 32.dp.toPx()
                var y = 80.dp.toPx()
                while (y < sheetHeight - 20.dp.toPx()) {
                    drawLine(
                        color = PaperRuledLine,
                        start = Offset(0f, y),
                        end = Offset(sheetWidth, y),
                        strokeWidth = 1.5f
                    )
                    y += lineSpacing
                }

                // Vertical red margin line (double line in classic school notebook)
                val marginX = 48.dp.toPx()
                drawLine(
                    color = PaperMarginLine.copy(alpha = 0.85f),
                    start = Offset(marginX, 0f),
                    end = Offset(marginX, sheetHeight),
                    strokeWidth = 2f
                )
                drawLine(
                    color = PaperMarginLine.copy(alpha = 0.35f),
                    start = Offset(marginX + 4.dp.toPx(), 0f),
                    end = Offset(marginX + 4.dp.toPx(), sheetHeight),
                    strokeWidth = 1f
                )

                // Binder spiral holes on the left margin
                val holeX = 22.dp.toPx()
                val holeSpacing = 56.dp.toPx()
                var holeY = 48.dp.toPx()
                while (holeY < sheetHeight - 40.dp.toPx()) {
                    // Outer metal ring
                    drawCircle(
                        color = PaperHoleRing,
                        radius = 7.dp.toPx(),
                        center = Offset(holeX, holeY),
                        style = Stroke(width = 2.dp.toPx())
                    )
                    // Inner hole looking into the wooden desk
                    drawCircle(
                        color = PaperHole,
                        radius = 5.dp.toPx(),
                        center = Offset(holeX, holeY)
                    )
                    // Spiral wire segment looping in from desk
                    drawArc(
                        color = PaperHoleRing,
                        startAngle = 140f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = Offset(holeX - 12.dp.toPx(), holeY - 8.dp.toPx()),
                        size = Size(16.dp.toPx(), 16.dp.toPx()),
                        style = Stroke(width = 3.dp.toPx())
                    )
                    holeY += holeSpacing
                }

                // Hand-drawn corner paper doodle / fold
                val foldPath = Path().apply {
                    moveTo(sheetWidth - 28.dp.toPx(), 0f)
                    lineTo(sheetWidth, 28.dp.toPx())
                    lineTo(sheetWidth - 28.dp.toPx(), 28.dp.toPx())
                    close()
                }
                drawPath(
                    path = foldPath,
                    color = Color(0xFFEDE8D8)
                )
                drawPath(
                    path = foldPath,
                    color = Color(0xFFD7D0BD),
                    style = Stroke(width = 1.5.dp.toPx())
                )
            }

            // Foreground game screen content
            content()
        }
    }
}
