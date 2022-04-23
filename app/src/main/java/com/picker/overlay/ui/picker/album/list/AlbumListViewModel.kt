package com.picker.overlay.ui.picker.album.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picker.overlay.domain.model.Album
import com.picker.overlay.domain.usecase.LoadingImageUseCase
import com.picker.overlay.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val loadingImageUseCase: LoadingImageUseCase
):ViewModel() {
    val albumListState = MutableStateFlow<Resource<Map<String, Album>>>(Resource.Loading)

    fun getAlbumList() = viewModelScope.launch {
        albumListState.value = loadingImageUseCase.getAlbumList()
    }

}