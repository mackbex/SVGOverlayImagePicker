package com.picker.overlay.util

/**
 * Response 결과 Wrapper
 */
sealed class Resource<out T> {
    object Init: Resource<Nothing>()
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val msg: String?) : Resource<Nothing>()
}