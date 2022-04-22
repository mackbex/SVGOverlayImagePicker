package com.picker.overlay.domain.usecase

import android.graphics.Bitmap
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.domain.repository.MediaRepository
import javax.inject.Inject

class OverlayImagesUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
){

    suspend fun overlayImages(photo: Photo, resource: Bitmap) = mediaRepository.overlayImages(photo, resource)
}