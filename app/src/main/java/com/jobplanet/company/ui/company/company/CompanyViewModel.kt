package com.jobplanet.company.ui.company.company

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(): ViewModel(){

    val moreCompanyInfoState = MutableSharedFlow<String>()

    fun onMoreCompanyInfoClick(companyName:String) {
        viewModelScope.launch {
            moreCompanyInfoState.emit(companyName)
        }
    }
}