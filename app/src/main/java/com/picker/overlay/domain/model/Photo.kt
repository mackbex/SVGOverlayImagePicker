package com.picker.overlay.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val albumTitle:String,
    val uri:Uri
): Parcelable