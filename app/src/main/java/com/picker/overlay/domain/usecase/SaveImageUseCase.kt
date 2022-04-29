package com.picker.overlay.domain.usecase

import com.picker.overlay.domain.model.OverlayInfo
import com.picker.overlay.domain.repository.MediaRepository
import javax.inject.Inject

/**
 * 이미지 저장 관련 usecase
 */
class SaveImageUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
){
    suspend fun overlayImages(info:OverlayInfo) = mediaRepository.overlayImages(info)
}