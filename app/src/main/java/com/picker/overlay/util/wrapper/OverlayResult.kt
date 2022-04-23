package com.picker.overlay.util.wrapper

/**
 * 오버레이 프로세스 용 Wrapper
 */
sealed class OverlayResult {
    object Init: OverlayResult()
    object InProgress: OverlayResult()
    object Success: OverlayResult()
    data class Failure(val msg: String) : OverlayResult()
}