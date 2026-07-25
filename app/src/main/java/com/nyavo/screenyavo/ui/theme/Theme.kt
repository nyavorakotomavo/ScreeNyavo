package com.nyavo.screenyavo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Palette ScreeNyavo
val BgMain = Color(0xFF0D0714)
val BgCard = Color(0xFF1F1035)
val BorderPixel = Color(0xFF3D205E)
val TextMain = Color(0xFFE8DDFF)
val TextMuted = Color(0xFF8E73B8)

// États de diagnostic
val StateVivant = Color(0xFF2ECC71)
val StateMort = Color(0xFF4A4A5A)
val StateBrulee = Color(0xFFE67E22)
val StateGhost = Color(0xFFECF0F1)
val StateHumidity = Color(0xFF3498DB)

// Accent neon
val NeonCyan = Color(0xFF00FFFF)
val NeonMagenta = Color(0xFFFF00FF)
val NeonGreen = Color(0xFF39FF14)

private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    secondary = NeonMagenta,
    background = BgMain,
    surface = BgCard,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = TextMain,
    onSurface = TextMain
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun ScreeNyavoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = ScreeNyavoTypography,
        shapes = AppShapes,
        content = content
    )
}
