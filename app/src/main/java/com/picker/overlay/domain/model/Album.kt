package com.picker.overlay.domain.model

import java.io.Serializable

data class Album(
    val title:String,
    val list:MutableList<AlbumItem>
): Serializable