package com.picker.overlay.ui.picker.overlay

import android.content.Context
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.chrisbanes.photoview.PhotoView
import com.picker.overlay.R
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.domain.usecase.LoadingAlbumUseCase
import com.picker.overlay.domain.usecase.OverlayImagesUseCase
import com.picker.overlay.util.OverlayResult
import com.picker.overlay.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverlayViewModel @Inject constructor(
    private val loadingAlbumUseCase: LoadingAlbumUseCase,
    private val overlayImagesUseCase: OverlayImagesUseCase
):ViewModel() {

    val overlayResourcesState = MutableStateFlow<Resource<List<String>>>(Resource.Loading)

    val overlayImagesState = MutableStateFlow<OverlayResult>(OverlayResult.Init)

    fun getOverlayResources(assetPath:String) = viewModelScope.launch {
        overlayResourcesState.value = loadingAlbumUseCase.getOverlayResources(assetPath)
    }

    fun overlayImage(context:Context, photo: Photo, resource:ImageView?) {

        if(resource == null) {
            overlayImagesState.value = OverlayResult.Failure(context.getString(R.string.err_failed_overlay_image))
        }
        else {
            viewModelScope.launch {
                overlayImagesUseCase.overlayImages(photo, resource.drawable.toBitmap())
            }
        }
    }
}