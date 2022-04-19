package com.jobplanet.company.ui.company.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobplanet.company.domain.model.Company
import com.jobplanet.company.domain.model.Items
import com.jobplanet.company.domain.model.Review
import com.jobplanet.company.util.Resource
import com.jobplanet.company.domain.model.SearchResult
import com.jobplanet.company.domain.usecase.CompanyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val companyUseCase: CompanyUseCase
) : ViewModel(){


    val companyListState = MutableStateFlow<Resource<SearchResult>>(Resource.Loading)

    /**
     * 기업정보 리스트 요청
     */
    fun getSearchResult() {
        viewModelScope.launch {
            companyListState.value = companyUseCase.getSearchResult()
        }
    }

    /**
     * 결과 내 재검색
     */
    fun filterCompanyList(text:String?):List<Items> {
        with(companyListState.value) {
            when(this) {
                is Resource.Success -> {
                    return this.data.items.filter { item ->
                        when(item) {
                            is Company -> {
                                text?.let {
                                    item.name.contains(it)
                                } ?: run { true }
                            }
                            is Review -> {
                                text?.let {
                                    item.name.contains(it)
                                } ?: run { true }
                            }
                            else -> true
                        }
                    }
                }
                else -> return listOf<Items>()
            }
        }
    }
}