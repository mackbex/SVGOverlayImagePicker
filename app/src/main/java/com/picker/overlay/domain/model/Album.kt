package com.picker.overlay.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


interface Album {
    val title:String
    val list:MutableList<Uri>
}