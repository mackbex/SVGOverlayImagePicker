package com.picker.overlay.ui.picker.album.list

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
class AlbumListViewModel @Inject constructor(

):ViewModel() {
    val albumListState = MutableStateFlow<Resource<List<Album>>>(Resource.Loading)


    fun getAlbumList() {
        viewModelScope.launch {

            val resultList = mutableListOf<Album>()

            for(i in 0 until 100) {
                resultList.add(
                    PhotoAlbum(
                        "$i",
                        arrayListOf()
                    )
                )
            }

            albumListState.value = Resource.Success(resultList)
        }
    }

}