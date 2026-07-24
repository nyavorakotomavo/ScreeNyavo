package com.nyavo.screenyavo.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nyavo.screenyavo.ui.components.AnimatedGridBackground
import com.nyavo.screenyavo.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit
) {
    var currentPage by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMain)
    ) {
        AnimatedGridBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = currentPage,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "pageTransition"
                ) { page ->
                    when (page) {
                        0 -> OnboardingStepOne()
                        1 -> OnboardingStepTwo()
                        2 -> OnboardingStepThree()
                        3 -> OnboardingStepFour(onOnboardingComplete)
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                SpringDotIndicator(
                    totalSteps = 4,
                    currentStep = currentPage,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                if (currentPage < 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Passer",
                            color = TextMuted,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier
                                .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                                .padding(12.dp)
                                .noRippleClickable { currentPage = 3 }
                        )

                        Button(
                            onClick = { currentPage++ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BgCard,
                                contentColor = TextMain
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .defaultMinSize(minHeight = 48.dp)
                                .border(1.5.dp, BorderPixel, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                text = "Suivant",
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingStepOne() {
    var isTouched by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(600)
            isTouched = true
            delay(1200)
            isTouched = false
        }
    }

    val scaleBounce by animateFloatAsState(
        targetValue = if (isTouched) 1.25f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scaleBounce"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer {
                    scaleX = scaleBounce
                    scaleY = scaleBounce
                }
                .background(if (isTouched) StateVivant else BgCard, RoundedCornerShape(16.dp))
                .border(2.dp, if (isTouched) StateVivant else BorderPixel, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isTouched) "100%" else "TAP",
                color = if (isTouched) BgMain else TextMuted,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Chaque zone de ton écran est testée.",
            color = TextMain,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun OnboardingStepTwo() {
    var isDeadZoneTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            isDeadZoneTriggered = true
            delay(1300)
            isDeadZoneTriggered = false
        }
    }

    val shakeOffset by animateFloatAsState(
        targetValue = if (isDeadZoneTriggered) 12f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        ),
        label = "shakeOffset"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .offset(x = shakeOffset.dp)
                .size(100.dp)
                .background(if (isDeadZoneTriggered) StateMort else BgCard, RoundedCornerShape(16.dp))
                .border(2.dp, if (isDeadZoneTriggered) StateBrulee else BorderPixel, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isDeadZoneTriggered) "MORT" else "FAIL",
                color = if (isDeadZoneTriggered) StateBrulee else TextMuted,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "On identifie précisément où ça ne marche plus.",
            color = TextMain,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun OnboardingStepThree() {
    val infiniteTransition = rememberInfiniteTransition(label = "edgeGlow")
    val edgeAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "edgeAlpha"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(width = 140.dp, height = 180.dp)
                .background(BgCard, RoundedCornerShape(12.dp))
                .border(3.dp, StateVivant.copy(alpha = edgeAlpha), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(StateVivant, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("SWIPE EDGE", color = BgMain, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "On te propose comment naviguer autrement.",
            color = TextMain,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun OnboardingStepFour(
    onComplete: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ctaPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onComplete,
            colors = ButtonDefaults.buttonColors(
                containerColor = StateVivant,
                contentColor = BgMain
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .graphicsLayer {
                    scaleX = pulseScale
                    scaleY = pulseScale
                }
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp)
        ) {
            Text(
                text = "Commencer le diagnostic",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun SpringDotIndicator(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until totalSteps) {
            val isActive = i == currentStep

            val dotWidth by animateFloatAsState(
                targetValue = if (isActive) 28f else 10f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "dotWidth"
            )

            val dotColor by animateColorAsState(
                targetValue = if (isActive) StateVivant else TextMuted.copy(alpha = 0.3f),
                animationSpec = spring(stiffness = Spring.StiffnessLow),
                label = "dotColor"
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(10.dp)
                    .width(dotWidth.dp)
                    .background(dotColor, CircleShape)
            )
        }
    }
}

private fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = this.clickable(
    interactionSource = MutableInteractionSource(),
    indication = null,
    onClick = onClick
)
