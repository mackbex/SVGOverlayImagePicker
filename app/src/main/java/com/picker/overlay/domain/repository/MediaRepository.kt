package com.picker.overlay.domain.repository

import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.OverlayInfo
import com.picker.overlay.util.wrapper.OverlayResult
import com.picker.overlay.util.wrapper.Resource


/**
 * 미디어 repos.
 * 지속적인 subscription 관련 기능은 없기때문에 flow 없이 단순히 리턴만 함.
 * 도메인 레이어이기 때문에, 도메인 관련 모든 parament는 안드로이드 종속성 없앰.
 */
interface MediaRepository {
    suspend fun getAlbumList() : Resource<Map<String, Album>>
    suspend fun getOverlayResources(assetPath:String) : Resource<List<String>>
    suspend fun overlayImages(info: OverlayInfo) : OverlayResult
}