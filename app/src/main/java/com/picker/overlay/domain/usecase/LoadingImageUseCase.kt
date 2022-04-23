package com.picker.overlay.domain.usecase

import com.picker.overlay.domain.repository.MediaRepository
import javax.inject.Inject

/**
 * 이미지 로딩 관련 usecase.
 */
class LoadingImageUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend fun getAlbumList() = mediaRepository.getAlbumList()
    suspend fun getOverlayResources(assetPath:String) = mediaRepository.getOverlayResources(assetPath)

}