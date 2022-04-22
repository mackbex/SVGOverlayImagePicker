package com.picker.overlay.domain.model

import java.io.Serializable

data class Photo(
    val albumTitle:String,
    val uri:String
): Serializable