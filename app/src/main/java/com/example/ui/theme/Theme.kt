package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SpeakeasyColorScheme = darkColorScheme(
    primary = SpeakeasyBrass,
    onPrimary = SpeakeasyCharcoal,
    primaryContainer = SpeakeasyCardElevated,
    onPrimaryContainer = SpeakeasyBrassLight,
    secondary = SpeakeasyBronze,
    onSecondary = SpeakeasyTextPrimary,
    tertiary = LedgerPaper,
    onTertiary = LedgerInk,
    background = SpeakeasyCharcoal,
    onBackground = SpeakeasyTextPrimary,
    surface = SpeakeasyCard,
    onSurface = SpeakeasyTextPrimary,
    surfaceVariant = SpeakeasyCardElevated,
    onSurfaceVariant = SpeakeasyTextSecondary,
    outline = SpeakeasyBronze,
    error = Color(0xFFFFB4AB)
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SpeakeasyColorScheme,
        typography = Typography,
        content = content
    )
}
