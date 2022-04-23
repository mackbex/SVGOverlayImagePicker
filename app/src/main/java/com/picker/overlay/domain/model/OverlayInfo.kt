package com.picker.overlay.domain.model

data class OverlayInfo(
    val item:AlbumItem,
    val bgIntrinsicWidth:Int,
    val bgIntrinsicHeight:Int,
    val resourceMeasuredWidth:Int,
    val resourceMeasuredHeight:Int,
    val resourceAssetPath:String
)
