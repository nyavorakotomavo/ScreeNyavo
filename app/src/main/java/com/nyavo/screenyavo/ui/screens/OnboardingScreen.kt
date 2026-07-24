package com.nyavo.screenyavo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nyavo.screenyavo.ui.components.AnimatedGridBackground
import com.nyavo.screenyavo.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.*
import kotlin.random.Random

@Composable
fun OnboardingScreen(onGetStarted: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    var currentStep by remember { mutableStateOf(0) }
    val stepsCount = 4

    Box(modifier = Modifier.fillMaxSize()) {
        // Persistent grid background
        AnimatedGridBackground()

        // Step content
        AnimatedContent(
            targetState = currentStep,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> if (targetState > initialState) fullWidth else -fullWidth },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeIn(animationSpec = spring(stiffness = Spring.StiffnessLow)) togetherWith
                        slideOutHorizontally(
                            targetOffsetX = { fullWidth -> if (targetState > initialState) -fullWidth else fullWidth },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        ) + fadeOut()
            },
            label = "stepTransition"
        ) { step ->
            when (step) {
                0 -> StepTouchSensorTest()
                1 -> StepDeadZoneDetection()
                2 -> StepAlternativeNavigation()
                3 -> StepUltimateCTA(onGetStarted = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onGetStarted()
                })
            }
        }

        // Navigation indicators
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(stepsCount) { index ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (index == currentStep) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (index == currentStep) NeonCyan else BorderPixel)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Next button (except last step)
            if (currentStep < stepsCount - 1) {
                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        currentStep++
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Text("Next", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun StepTouchSensorTest() {
    val haptic = LocalHapticFeedback.current
    // Track touch points for heat-map
    var touchPoints by remember { mutableStateOf(listOf<Offset>()) }
    // Physics rings
    val rings = remember { mutableStateListOf<Ring>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "TOUCH SENSOR TEST",
                style = MaterialTheme.typography.titleLarge,
                color = NeonCyan
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Tap and hold anywhere to see live heat-map diagnostics.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Interactive canvas
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BgCard)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = { offset ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                touchPoints = touchPoints + offset
                                // Add a new expanding ring
                                rings.add(Ring(center = offset, radius = 0f, alpha = 1f))
                                tryAwaitRelease()
                                touchPoints = touchPoints.filter { it != offset }
                            }
                        )
                    }
            ) {
                // Draw heat-map trail
                touchPoints.forEach { point ->
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(StateVivant, Color.Transparent),
                            center = point,
                            radius = 60f
                        ),
                        radius = 60f,
                        center = point
                    )
                }
                // Draw expanding rings with physics
                rings.forEach { ring ->
                    drawCircle(
                        color = NeonCyan.copy(alpha = ring.alpha),
                        radius = ring.radius,
                        center = ring.center,
                        style = Stroke(width = 4f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Live responsiveness: ${touchPoints.size} active points",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
            )
        }
        // Animate rings
        LaunchedEffect(rings.size) {
            while (rings.isNotEmpty()) {
                rings.forEachIndexed { index, ring ->
                    rings[index] = ring.copy(
                        radius = ring.radius + 15f,
                        alpha = (ring.alpha - 0.03f).coerceIn(0f, 1f)
                    )
                }
                rings.removeAll { it.alpha <= 0f }
                delay(16) // ~60fps
            }
        }
    }
}

data class Ring(val center: Offset, val radius: Float, val alpha: Float)

@Composable
fun StepDeadZoneDetection() {
    val haptic = LocalHapticFeedback.current
    var showGlitch by remember { mutableStateOf(false) }
    var shakeOffset by remember { mutableStateOf(Offset.Zero) }

    // Screen shake effect
    LaunchedEffect(showGlitch) {
        if (showGlitch) {
            repeat(10) {
                shakeOffset = Offset(
                    (Random.nextFloat() - 0.5f) * 30f,
                    (Random.nextFloat() - 0.5f) * 30f
                )
                delay(50)
            }
            shakeOffset = Offset.Zero
            showGlitch = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .graphicsLayer {
                translationX = shakeOffset.x
                translationY = shakeOffset.y
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "DEAD ZONE DETECTION",
                style = MaterialTheme.typography.titleLarge,
                color = NeonMagenta
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Tap the red zone to simulate a dead pixel cluster.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Dead zone grid
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BgCard)
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            // Check if tap inside dead zone (center 100x100 area)
                            val zoneLeft = size.width / 2 - 50f
                            val zoneTop = size.height / 2 - 50f
                            val zoneRight = zoneLeft + 100f
                            val zoneBottom = zoneTop + 100f
                            if (offset.x in zoneLeft..zoneRight && offset.y in zoneTop..zoneBottom) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                showGlitch = true
                            }
                        }
                    }
            ) {
                val center = Offset(size.width / 2, size.height / 2)
                // Draw grid
                for (i in 0..10) {
                    val x = i * size.width / 10
                    drawLine(Color.White.copy(alpha = 0.2f), Offset(x, 0f), Offset(x, size.height))
                }
                for (i in 0..10) {
                    val y = i * size.height / 10
                    drawLine(Color.White.copy(alpha = 0.2f), Offset(0f, y), Offset(size.width, y))
                }
                // Dead zone highlight
                drawRect(
                    color = StateBrulee.copy(alpha = 0.6f),
                    topLeft = Offset(center.x - 50f, center.y - 50f),
                    size = Size(100f, 100f)
                )
                // Static glitch noise if active
                if (showGlitch) {
                    for (i in 0..20) {
                        val x = Random.nextFloat() * size.width
                        val y = Random.nextFloat() * size.height
                        drawRect(
                            color = Color.White.copy(alpha = Random.nextFloat() * 0.8f),
                            topLeft = Offset(x, y),
                            size = Size(20f, 2f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                if (showGlitch) "DEAD ZONE HIT - RECALIBRATING" else "Tap the orange area",
                style = MaterialTheme.typography.labelSmall,
                color = if (showGlitch) NeonMagenta else TextMuted
            )
        }
    }
}

@Composable
fun StepAlternativeNavigation() {
    val haptic = LocalHapticFeedback.current
    val infiniteTransition = rememberInfiniteTransition(label = "edgeSwipe")
    val cursorProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "cursorAnim"
    )
    // Cursor position along edges
    val edgePath = remember {
        listOf(
            Offset(0f, 0f), // top-left to top-right
            Offset(1f, 0f), // top-right to bottom-right
            Offset(1f, 1f), // bottom-right to bottom-left
            Offset(0f, 1f)  // bottom-left to top-left
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "ALTERNATIVE NAVIGATION",
                style = MaterialTheme.typography.titleLarge,
                color = NeonCyan
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Demonstrating edge-swipe gesture paths for accessibility.",
                style = MaterialTheme.typography.bodyLarge,
                color = TextMuted
            )
            Spacer(modifier = Modifier.height(24.dp))
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BgCard)
            ) {
                val w = size.width
                val h = size.height
                val points = listOf(
                    Offset(0f, 0f),
                    Offset(w, 0f),
                    Offset(w, h),
                    Offset(0f, h)
                )
                // Calculate animated position along the edge loop
                val totalLength = 2 * (w + h)
                val progress = cursorProgress * totalLength
                var travelled = 0f
                var cursorPos = Offset.Zero
                for (i in 0..3) {
                    val start = points[i]
                    val end = points[(i + 1) % 4]
                    val segmentLength = (end - start).getDistance()
                    if (travelled + segmentLength >= progress) {
                        val t = (progress - travelled) / segmentLength
                        cursorPos = lerp(start, end, t)
                        break
                    }
                    travelled += segmentLength
                }
                // Draw edge zones with glow
                val edgeZoneWidth = 30f
                // top
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(NeonCyan.copy(alpha = 0.4f), Color.Transparent, NeonCyan.copy(alpha = 0.4f))
                    ),
                    topLeft = Offset(0f, 0f),
                    size = Size(w, edgeZoneWidth)
                )
                // right
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(NeonCyan.copy(alpha = 0.4f), Color.Transparent, NeonCyan.copy(alpha = 0.4f))
                    ),
                    topLeft = Offset(w - edgeZoneWidth, 0f),
                    size = Size(edgeZoneWidth, h)
                )
                // bottom
                drawRect(
                    brush = Brush.horizontalGradient(
                        colors = listOf(NeonCyan.copy(alpha = 0.4f), Color.Transparent, NeonCyan.copy(alpha = 0.4f))
                    ),
                    topLeft = Offset(0f, h - edgeZoneWidth),
                    size = Size(w, edgeZoneWidth)
                )
                // left
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(NeonCyan.copy(alpha = 0.4f), Color.Transparent, NeonCyan.copy(alpha = 0.4f))
                    ),
                    topLeft = Offset(0f, 0f),
                    size = Size(edgeZoneWidth, h)
                )
                // Animated cursor
                drawCircle(
                    color = NeonMagenta,
                    radius = 12f,
                    center = cursorPos
                )
                // Trail
                for (i in 1..10) {
                    val fraction = i / 10f
                    drawCircle(
                        color = NeonMagenta.copy(alpha = 0.3f - fraction * 0.3f),
                        radius = 8f,
                        center = Offset(
                            cursorPos.x - (cursorProgress * 20f * fraction),
                            cursorPos.y - (cursorProgress * 20f * fraction)
                        )
                    )
                }
            }
        }
        // Haptic on edge zone touch? Could add pointer input but not mandatory.
    }
}

fun lerp(start: Offset, end: Offset, fraction: Float): Offset {
    return Offset(
        start.x + (end.x - start.x) * fraction,
        start.y + (end.y - start.y) * fraction
    )
}

@Composable
fun StepUltimateCTA(onGetStarted: () -> Unit) {
    val haptic = LocalHapticFeedback.current
    val infiniteTransition = rememberInfiniteTransition(label = "ctaBreathing")
    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breath"
    )
    val borderSweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "READY TO CALIBRATE",
                style = MaterialTheme.typography.titleLarge,
                color = NeonCyan
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onGetStarted()
                },
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = breathingScale
                        scaleY = breathingScale
                    }
                    .shadow(16.dp, shape = RoundedCornerShape(16.dp), ambientColor = NeonCyan, spotColor = NeonMagenta)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Transparent)
                    .then(
                        Modifier.drawWithContent {
                            // Gradient sweep border
                            drawRoundRect(
                                brush = Brush.sweepGradient(
                                    colors = listOf(NeonCyan, NeonMagenta, NeonCyan),
                                    center = Offset(size.width / 2, size.height / 2)
                                ),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx()),
                                style = Stroke(width = 4.dp.toPx())
                            )
                            // Inner content
                            drawContent()
                        }
                    ),
                colors = ButtonDefaults.buttonColors(containerColor = BgCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "GET STARTED",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        letterSpacing = 4.sp,
                        color = NeonCyan
                    ),
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
            }
        }
    }
}
