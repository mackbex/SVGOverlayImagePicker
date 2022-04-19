package com.picker.overlay.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoAlbum(
    override val title:String,
    override val list: ArrayList<String>
):Album, Parcelable