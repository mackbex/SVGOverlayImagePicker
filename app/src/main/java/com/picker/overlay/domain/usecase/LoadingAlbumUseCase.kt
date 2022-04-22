package com.picker.overlay.domain.usecase

import android.graphics.Bitmap
import com.picker.overlay.domain.repository.MediaRepository
import com.picker.overlay.util.OverlayResult
import javax.inject.Inject

class LoadingAlbumUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend fun getAlbumList() = mediaRepository.getAlbumList()
    suspend fun getOverlayResources(assetPath:String) = mediaRepository.getOverlayResources(assetPath)

}