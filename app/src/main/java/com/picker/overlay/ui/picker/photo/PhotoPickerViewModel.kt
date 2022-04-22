package com.picker.overlay.ui.picker.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoPickerViewModel @Inject constructor(
):ViewModel() {

    val photoListState = MutableStateFlow<Resource<List<Photo>>>(Resource.Loading)

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