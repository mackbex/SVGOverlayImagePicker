package com.picker.overlay.util

/**
 * Wrapper
 */
sealed class OverlayResult {
    object Init: OverlayResult()
    object Success: OverlayResult()
    data class Failure(val msg: String) : OverlayResult()
}