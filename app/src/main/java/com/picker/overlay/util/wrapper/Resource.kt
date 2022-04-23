package com.picker.overlay.util.wrapper

/**
 * Wrapper
 */
sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val msg: String) : Resource<Nothing>()
}