package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GameColorScheme = lightColorScheme(
    primary = InkBlack,
    onPrimary = Color.White,
    primaryContainer = PastelYellow,
    onPrimaryContainer = InkBlack,
    secondary = PastelBlueDark,
    onSecondary = Color.White,
    secondaryContainer = PastelBlue,
    onSecondaryContainer = InkBlack,
    tertiary = PastelGreenDark,
    onTertiary = Color.White,
    tertiaryContainer = PastelGreen,
    onTertiaryContainer = InkBlack,
    background = WoodDeskMedium,
    onBackground = Color.White,
    surface = PaperBackground,
    onSurface = InkBlack,
    surfaceVariant = PaperBackgroundAlt,
    onSurfaceVariant = InkSecondary,
    error = FailRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GameColorScheme,
        typography = Typography,
        content = content
    )
}
