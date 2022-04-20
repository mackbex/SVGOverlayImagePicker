package com.picker.overlay.ui.picker.photo

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.domain.model.PhotoAlbum
import com.picker.overlay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoPickerViewModel @Inject constructor(

):ViewModel() {

    val photoListState = MutableStateFlow<Resource<List<String>>>(Resource.Loading)


    fun getPhotoList(album:Album) {
        viewModelScope.launch {
            val resultList = mutableListOf<String>()
            for(i in 0 until 100) {
                resultList.add("$i")
            }
            photoListState.value = Resource.Success(resultList)
        }
    }

    fun getPhotoItem(albumTitle:String, item:String):Photo {
        return Photo(albumTitle, item)
    }

}