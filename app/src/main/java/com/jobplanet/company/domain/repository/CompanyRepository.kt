package com.jobplanet.company.domain.repository

import com.jobplanet.company.util.Resource
import com.jobplanet.company.domain.model.SearchResult

interface CompanyRepository {
    suspend fun getCompanyList() : Resource<SearchResult>
}