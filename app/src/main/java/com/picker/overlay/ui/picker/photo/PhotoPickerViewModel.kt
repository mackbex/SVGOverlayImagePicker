package com.picker.overlay.ui.picker.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.AlbumItem
import com.picker.overlay.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoPickerViewModel @Inject constructor(
):ViewModel() {

    val photoListState = MutableStateFlow<Resource<List<AlbumItem>>>(Resource.Loading)

    /**
     * 앨범 내 이미지 리스트 가져옴.
     * 메인의 앨범 fragment 모델이 리스트를 갖고있기에, 따로 usecase 사용 안함.
     */
    fun getPhotoList(album:Album) {
        viewModelScope.launch {
            val resultList = mutableListOf<AlbumItem>()
            album.list.forEach { resultList.add(it) }
            photoListState.value = Resource.Success(resultList)
        }
    }

}