package com.picker.overlay.ui.picker.overlay

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picker.overlay.R
import com.picker.overlay.domain.model.OverlayInfo
import com.picker.overlay.domain.model.Photo
import com.picker.overlay.domain.usecase.LoadingAlbumUseCase
import com.picker.overlay.domain.usecase.OverlayImagesUseCase
import com.picker.overlay.util.wrapper.OverlayResult
import com.picker.overlay.util.wrapper.Resource
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

    fun overlayImage(context:Context, photo: Photo, image:ImageView, resource:ImageView) {
        if(overlayImagesState.value is OverlayResult.Init) {
            if(resource.drawable == null) {
                overlayImagesState.value = OverlayResult.Failure(context.getString(R.string.msg_choose_svg_resource))
                overlayImagesState.value = OverlayResult.Init
            }
            else {
                overlayImagesState.value = OverlayResult.InProgress
                viewModelScope.launch {

                    overlayImagesState.value = overlayImagesUseCase.overlayImages(OverlayInfo(
                        photo,
                        image.drawable.intrinsicWidth,
                        image.drawable.intrinsicHeight,
                        resource.measuredWidth,
                        resource.measuredHeight,
                        resource.tag as String
                    ))
                }
            }
        }
    }
}