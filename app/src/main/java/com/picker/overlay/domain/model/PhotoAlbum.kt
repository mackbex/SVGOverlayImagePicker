package com.picker.overlay.domain.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoAlbum(
    override val title:String,
    override val list:MutableList<Uri>
):Album, Parcelable