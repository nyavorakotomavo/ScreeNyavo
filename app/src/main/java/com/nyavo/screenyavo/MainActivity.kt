package com.nyavo.screenyavo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.nyavo.screenyavo.ui.screens.OnboardingScreen
import com.nyavo.screenyavo.ui.screens.SplashScreen
import com.nyavo.screenyavo.ui.theme.ScreeNyavoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreeNyavoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf("splash") }

when (currentScreen) {
                            "splash" -> SplashScreen(
                                onSplashFinished = { currentScreen = "onboarding" }
                            )
                            "onboarding" -> OnboardingScreen(
                                onOnboardingComplete = { currentScreen = "touchTest" }
                            )
                            "touchTest" -> TouchTestScreen()
                    }
                }
            }
        }
    }
}
