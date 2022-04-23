package com.picker.overlay.domain.model

import java.io.Serializable

data class Photo(
    override val albumTitle:String,
    override val uri:String,
    override val path:String
):AlbumItem, Serializable