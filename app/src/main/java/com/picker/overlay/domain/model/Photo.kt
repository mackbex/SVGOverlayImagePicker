package com.picker.overlay.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val albumTitle:String,
    val path:String
): Parcelable