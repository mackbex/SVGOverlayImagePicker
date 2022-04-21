package com.picker.overlay.domain.repository

import com.picker.overlay.domain.model.Album
import com.picker.overlay.util.Resource
import com.picker.overlay.domain.model.SearchResult

interface MediaRepository {
    suspend fun getAlbumList() : Resource<Map<String,Album>>
}