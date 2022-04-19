package com.jobplanet.company.data.repository

import com.jobplanet.company.data.source.remote.service.CompanySearchService
import com.jobplanet.company.di.AppModule
import com.jobplanet.company.util.Resource
import com.jobplanet.company.domain.model.*
import com.jobplanet.company.domain.repository.CompanyRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class CompanyInfoRepositoryImpl @Inject constructor(
    private val companySearchService: CompanySearchService,
    @AppModule.IODispatcher private val defaultDispatcher: CoroutineDispatcher
): CompanyRepository {

    /**
     * 서버로부터 기업정보 리스트 받아옴
     */
    override suspend fun getCompanyList() = withContext(defaultDispatcher) {
        val response = companySearchService.getCompanyList()
        val result = if(response.isSuccessful) {
            val list = response.body()
            Resource.Success<SearchResult>(list ?: run {SearchResult()})
        }
        else {
            Resource.Failure(response.message())
        }
        result
    }
}