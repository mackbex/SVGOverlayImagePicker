package com.picker.overlay.ui.picker.photo

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.domain.model.PhotoAlbum
import com.picker.overlay.domain.usecase.MediaUseCase
import com.picker.overlay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoPickerViewModel @Inject constructor(
    private val mediaUseCase: MediaUseCase
):ViewModel() {

    val photoListState = MutableStateFlow<Resource<List<Photo>>>(Resource.Init)

    fun getPhotoList(album:Album) {
        viewModelScope.launch {
            val resultList = mutableListOf<Photo>()
            for(uri in album.list) {
                resultList.add(Photo(album.title, uri))
            }
            photoListState.value = Resource.Success(resultList)
        }
    }

}