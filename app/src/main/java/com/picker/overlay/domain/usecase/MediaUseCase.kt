package com.picker.overlay.domain.usecase

import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.repository.MediaRepository
import javax.inject.Inject

class MediaUseCase @Inject constructor(
    private val mediaRepository: MediaRepository
) {
    suspend fun getAlbumList() = mediaRepository.getAlbumList()
}