package com.picker.overlay.ui.picker.overlay

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.model.PhotoAlbum
import com.picker.overlay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverlayViewModel @Inject constructor(

):ViewModel() {

    val overlayResourceListState = MutableStateFlow<Resource<List<String>>>(Resource.Loading)


    init {
        getOverlayResources()
    }

    fun getOverlayResources() {
        viewModelScope.launch {
            val resultList = mutableListOf<String>()
            for(i in 0 until 10) {
                resultList.add("$i")
            }
            overlayResourceListState.value = Resource.Success(resultList)
        }
    }

}