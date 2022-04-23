package com.picker.overlay.util.wrapper

/**
 * Wrapper
 */
sealed class OverlayResult {
    object Init: OverlayResult()
    object InProgress: OverlayResult()
    object Success: OverlayResult()
    data class Failure(val msg: String) : OverlayResult()
}