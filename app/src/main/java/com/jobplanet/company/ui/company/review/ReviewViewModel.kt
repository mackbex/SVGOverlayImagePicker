package com.jobplanet.company.ui.company.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(

): ViewModel(){

    val moreReviewInfoState = MutableSharedFlow<String>()

    fun onMoreReviewInfoClick(companyName:String) {
        viewModelScope.launch {
            moreReviewInfoState.emit(companyName)
        }
    }
}