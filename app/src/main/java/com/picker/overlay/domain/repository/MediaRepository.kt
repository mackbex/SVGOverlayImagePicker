package com.picker.overlay.domain.repository

import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.OverlayInfo
import com.picker.overlay.util.wrapper.OverlayResult
import com.picker.overlay.util.wrapper.Resource

interface MediaRepository {
    suspend fun getAlbumList() : Resource<Map<String, Album>>
    suspend fun getOverlayResources(assetPath:String) : Resource<List<String>>
    suspend fun overlayImages(info: OverlayInfo) : OverlayResult
}