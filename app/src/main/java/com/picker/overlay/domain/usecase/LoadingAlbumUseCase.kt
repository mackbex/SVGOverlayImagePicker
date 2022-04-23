package com.picker.overlay.domain.usecase

import com.picker.overlay.domain.repository.MediaRepository
import javax.inject.Inject

class LoadingAlbumUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend fun getAlbumList() = mediaRepository.getAlbumList()
    suspend fun getOverlayResources(assetPath:String) = mediaRepository.getOverlayResources(assetPath)

}