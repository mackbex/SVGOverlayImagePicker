package com.picker.overlay.domain.repository

import com.picker.overlay.util.Resource
import com.picker.overlay.domain.model.SearchResult

interface CompanyRepository {
    suspend fun getCompanyList() : Resource<SearchResult>
}