package com.nyavo.screenyavo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BgMain = Color(0xFF0D0714)
private val BgCard = Color(0xFF1F1035)
private val BorderPixel = Color(0xFF3D205E)
private val TextMain = Color(0xFFE8DDFF)
private val StateVivant = Color(0xFF2ECC71)

@Composable
fun TouchTestScreen() {
    var isTestStarted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgMain),
        contentAlignment = Alignment.Center
    ) {
        if (!isTestStarted) {
            Button(
                onClick = { isTestStarted = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BgCard,
                    contentColor = TextMain
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .border(2.dp, BorderPixel, RoundedCornerShape(4.dp))
            ) {
                Text(
                    text = "commencer test",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        } else {
            PixelGridTestView()
        }
    }
}

@Composable
fun PixelGridTestView() {
    val columns = 12
    val rows = 20
    val totalPixels = columns * rows

    val touchedPixels = remember { 
        mutableStateListOf<Boolean>().apply { addAll(List(totalPixels) { false }) } 
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val cellWidth = size.width / columns
                        val cellHeight = size.height / rows
                        val col = (change.position.x / cellWidth).toInt().coerceIn(0, columns - 1)
                        val row = (change.position.y / cellHeight).toInt().coerceIn(0, rows - 1)
                        val index = row * columns + col

                        if (index in 0 until totalPixels) {
                            touchedPixels[index] = true
                        }
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val cellWidth = size.width / columns
                        val cellHeight = size.height / rows
                        val col = (offset.x / cellWidth).toInt().coerceIn(0, columns - 1)
                        val row = (offset.y / cellHeight).toInt().coerceIn(0, rows - 1)
                        val index = row * columns + col

                        if (index in 0 until totalPixels) {
                            touchedPixels[index] = true
                        }
                    }
                }
        ) {
            items(totalPixels) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .padding(0.5.dp)
                        .background(
                            if (touchedPixels[index]) StateVivant else Color(0x0AFFFFFF)
                        )
                )
            }
        }

        Text(
            text = "Balayer avec\nvotre doigt",
            color = TextMain.copy(alpha = 0.35f),
            fontSize = 14.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        )
    }
}
