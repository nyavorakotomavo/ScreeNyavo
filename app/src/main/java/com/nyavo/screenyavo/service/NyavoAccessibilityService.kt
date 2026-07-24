package com.nyavo.screenyavo.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class NyavoAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Logique de capture d'événements (à enrichir plus tard)
    }

    override fun onInterrupt() {
        // Gestion de l'interruption du service
    }
}
