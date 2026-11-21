package com.example.congresar.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Ink,
    onPrimary = Paper,
    primaryContainer = Cloud,
    onPrimaryContainer = Ink,
    secondary = Graphite,
    onSecondary = Paper,
    secondaryContainer = Field,
    onSecondaryContainer = Graphite,
    tertiary = Stone,
    onTertiary = Paper,
    tertiaryContainer = Cloud,
    onTertiaryContainer = Ink,
    background = Porcelain,
    onBackground = Ink,
    surface = Paper,
    onSurface = Ink,
    surfaceVariant = Field,
    onSurfaceVariant = Stone,
    outline = Mist,
    outlineVariant = Line,
    inverseSurface = Ink,
    inverseOnSurface = Paper,
    inversePrimary = Silver,
    surfaceTint = Ink,
    scrim = Ink,
)

@Composable
fun CongresarTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}
