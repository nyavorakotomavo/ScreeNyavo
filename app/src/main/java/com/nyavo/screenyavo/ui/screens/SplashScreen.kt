package com.nyavo.screenyavo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nyavo.screenyavo.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val haptic = LocalHapticFeedback.current

    // Animation states
    var showMatrix by remember { mutableStateOf(true) }
    var showLogo by remember { mutableStateOf(false) }
    var glitchActive by remember { mutableStateOf(false) }
    var transitionOut by remember { mutableStateOf(false) }

    val logoAlpha by animateFloatAsState(
        targetValue = if (showLogo) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "logoAlpha"
    )
    val logoScale by animateFloatAsState(
        targetValue = if (showLogo) 1f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "logoScale"
    )

    LaunchedEffect(Unit) {
        // Boot sequence
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        delay(500) // matrix warm-up duration
        showMatrix = false
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        delay(200)
        showLogo = true
        // Glitch effect after logo reveal
        delay(1000)
        glitchActive = true
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        delay(600)
        glitchActive = false
        transitionOut = true
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        delay(500)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMain)
    ) {
        // Matrix-like background (falling characters)
        if (showMatrix) {
            MatrixWarmUp(modifier = Modifier.fillMaxSize())
        }

        // Logo reveal with chromatic aura
        AnimatedVisibility(
            visible = showLogo,
            enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMedium)) +
                    scaleIn(initialScale = 0.8f, animationSpec = spring()),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Chromatic aura background
                Canvas(modifier = Modifier.fillMaxSize().graphicsLayer {
                    alpha = logoAlpha
                    scaleX = logoScale
                    scaleY = logoScale
                }) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.minDimension * 0.25f
                    // Cyan and magenta glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(NeonCyan, Color.Transparent),
                            center = center,
                            radius = radius * 1.5f
                        ),
                        radius = radius * 1.5f
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(NeonMagenta, Color.Transparent),
                            center = center + Offset(10f, -10f),
                            radius = radius * 1.3f
                        ),
                        radius = radius * 1.3f
                    )
                }

                // Logo text with glitch effect
                Text(
                    text = "SCREE\nNYAVO",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 42.sp,
                        letterSpacing = 8.sp,
                        color = NeonCyan,
                        shadow = Shadow(
                            color = NeonMagenta,
                            offset = Offset(if (glitchActive) 12f else 5f, if (glitchActive) -5f else 3f),
                            blurRadius = if (glitchActive) 20f else 8f
                        )
                    ),
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = logoAlpha
                            scaleX = logoScale
                            scaleY = logoScale
                            translationY = if (glitchActive) 15f else 0f
                        }
                )
            }
        }

        // Transition overlay
        if (transitionOut) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BgMain.copy(alpha = 0.9f))
            )
        }
    }
}

@Composable
fun MatrixWarmUp(modifier: Modifier = Modifier) {
    val characters = "アイウエオカキクケコサシスセソタチツテトナニヌネノハヒフヘホマミムメモヤユヨラリルレロワヲン01"
    val columns = 20
    val rows = 30
    // Simple falling matrix using canvas
    Canvas(modifier = modifier) {
        val rng = Random(42) // deterministic for consistent look
        val cellWidth = size.width / columns
        val cellHeight = size.height / rows
        val paint = android.graphics.Paint().apply {
            color = android.graphics.Color.argb(150, 0, 255, 0)
            textSize = 18f
            typeface = android.graphics.Typeface.MONOSPACE
        }
        for (c in 0 until columns) {
            for (r in 0 until rows) {
                if (rng.nextFloat() < 0.3f) {
                    val char = characters[rng.nextInt(characters.length)]
                    val x = c * cellWidth + cellWidth / 4
                    val y = (r * cellHeight + cellHeight / 2) + (System.currentTimeMillis() / 100 % 10) * 5f
                    drawContext.canvas.nativeCanvas.drawText(char.toString(), x, y, paint)
                }
            }
        }
    }
}
