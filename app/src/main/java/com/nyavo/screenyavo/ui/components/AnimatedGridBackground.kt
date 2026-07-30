package com.nyavo.screenyavo.ui.components

import android.graphics.Paint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.nativeCanvas
import com.nyavo.screenyavo.ui.theme.*
import kotlin.math.*
import kotlin.random.Random

@Composable
fun AnimatedGridBackground(modifier: Modifier = Modifier) {
    // Animation controls
    val infiniteTransition = rememberInfiniteTransition(label = "grid")
    val scanLineOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scanline"
    )
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse"
    )

    // Pre-allocated paints for performance
    val gridLinePaint = remember {
        Paint().apply {
            color = BorderPixel.toArgb()
            strokeWidth = 2f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
    }
    val nodePaint = remember {
        Paint().apply {
            color = StateVivant.toArgb()
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }
    val deadNodePaint = remember {
        Paint().apply {
            color = StateMort.toArgb()
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }
    val scanLinePaint = remember {
        Paint().apply {
            color = android.graphics.Color.argb(30, 0, 255, 255)
            style = Paint.Style.FILL
        }
    }
    val glitchPaint = remember {
        Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }
    }
    val pulsePaint = remember {
        Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 3f
            isAntiAlias = true
        }
    }

    // Grid definition
    val columns = 12
    val rows = 20
    // Store random dead nodes indices
    val deadNodes = remember {
        List(5) { Random.nextInt(columns * rows) }
    }
    // Flicker states
    val flickerState = remember { mutableStateListOf<Boolean>().apply { repeat(deadNodes.size) { add(false) } } }
    LaunchedEffect(Unit) {
        while (true) {
            flickerState.indices.forEach { i ->
                flickerState[i] = Random.nextFloat() < 0.2f
            }
            kotlinx.coroutines.delay(200)
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val cellWidth = width / columns
        val cellHeight = height / rows

        // Pre-calculate node centers
        val centers = mutableListOf<Offset>()
        for (r in 0 until rows) {
            for (c in 0 until columns) {
                centers.add(Offset(c * cellWidth + cellWidth / 2, r * cellHeight + cellHeight / 2))
            }
        }

        // Draw grid lines using pre-allocated paint
        val canvas = drawContext.canvas.nativeCanvas
        // vertical lines
        for (c in 0..columns) {
            val x = c * cellWidth
            canvas.drawLine(x, 0f, x, height, gridLinePaint)
        }
        // horizontal lines
        for (r in 0..rows) {
            val y = r * cellHeight
            canvas.drawLine(0f, y, width, y, gridLinePaint)
        }

        // Draw nodes
        centers.forEachIndexed { index, offset ->
            val isDead = index in deadNodes
            val paint = if (isDead) {
                if (flickerState.getOrElse(deadNodes.indexOf(index)) { false }) {
                    // glitch flicker: random color
                    glitchPaint.color = if (Random.nextBoolean()) StateBrulee.toArgb() else StateGhost.toArgb()
                    glitchPaint
                } else {
                    deadNodePaint
                }
            } else {
                nodePaint
            }
            canvas.drawCircle(offset.x, offset.y, 4f, paint)
        }

        // CRT scanlines - horizontal lines with varying alpha
        val scanLineCount = 30
        val scanLineHeight = height / scanLineCount
        for (i in 0 until scanLineCount) {
            val y = (i * scanLineHeight + scanLineOffset * scanLineHeight) % height
            scanLinePaint.alpha = (15 + 10 * sin(i * 0.5f + scanLineOffset * 10f).toFloat().coerceIn(0f, 1f) * 30).toInt()
            canvas.drawRect(0f, y, width, y + 2f, scanLinePaint)
        }

        // Radial energy pulse wave
        val pulseCenter = Offset(width * 0.5f, height * 0.5f)
        val maxRadius = sqrt(width * width + height * height) * 0.6f
        val currentRadius = pulseRadius * maxRadius
        pulsePaint.color = android.graphics.Color.argb(40, 0, 255, 255)
        pulsePaint.strokeWidth = 3f
        canvas.drawCircle(pulseCenter.x, pulseCenter.y, currentRadius, pulsePaint)

        // Additional inner pulse ring
        pulsePaint.color = android.graphics.Color.argb(20, 255, 0, 255)
        pulsePaint.strokeWidth = 1.5f
        canvas.drawCircle(pulseCenter.x, pulseCenter.y, currentRadius * 0.7f, pulsePaint)
    }
}
