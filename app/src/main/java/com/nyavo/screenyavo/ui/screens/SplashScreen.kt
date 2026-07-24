package com.nyavo.screenyavo.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nyavo.screenyavo.ui.components.AnimatedGridBackground
import com.nyavo.screenyavo.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    val logoText = "ScreeNyavo"
    var visibleLettersCount by remember { mutableIntStateOf(0) }
    var isGlowing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        for (i in 1..logoText.length) {
            visibleLettersCount = i
            delay(120)
        }
        isGlowing = true
        delay(1000)
        onSplashFinished()
    }

    val glowScale by animateFloatAsState(
        targetValue = if (isGlowing) 1.05f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "glowScale"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (visibleLettersCount > 0) 1.0f else 0.0f,
        animationSpec = spring(stiffness = Spring.StiffnessVeryLow),
        label = "logoAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMain),
        contentAlignment = Alignment.Center
    ) {
        AnimatedGridBackground()

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = glowScale
                    scaleY = glowScale
                    alpha = logoAlpha
                }
                .background(BgCard.copy(alpha = 0.95f), RoundedCornerShape(12.dp))
                .border(2.dp, if (isGlowing) StateVivant else BorderPixel, RoundedCornerShape(12.dp))
                .padding(horizontal = 28.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                logoText.forEachIndexed { index, char ->
                    val isVisible = index < visibleLettersCount
                    Text(
                        text = char.toString(),
                        color = if (isVisible) TextMain else BgMain,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
            }
        }
    }
}
