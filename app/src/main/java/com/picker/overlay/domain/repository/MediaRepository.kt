package com.picker.overlay.domain.repository

import android.graphics.Bitmap
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.util.OverlayResult
import com.picker.overlay.util.Resource

interface MediaRepository {
    suspend fun getAlbumList() : Resource<Map<String,Album>>
    suspend fun getOverlayResources(assetPath:String) : Resource<List<String>>
    suspend fun overlayImages(photo:Photo, resource: Bitmap) : OverlayResult
}