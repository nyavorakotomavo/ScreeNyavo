package com.nyavo.screenyavo.service

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AccessibilityBridge {
    private val _serviceEvents = MutableSharedFlow<String>()
    val serviceEvents = _serviceEvents.asSharedFlow()

    suspend fun sendEvent(event: String) {
        _serviceEvents.emit(event)
    }
}
