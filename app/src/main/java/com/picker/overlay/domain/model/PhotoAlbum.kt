package com.picker.overlay.domain.model

import java.io.Serializable

data class PhotoAlbum(
    override val title:String,
    override val list:MutableList<AlbumItem>
):Album, Serializable