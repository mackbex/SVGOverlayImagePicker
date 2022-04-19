package com.jobplanet.company.domain.usecase

import com.jobplanet.company.domain.repository.CompanyRepository
import javax.inject.Inject


class CompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend fun getSearchResult() = companyRepository.getCompanyList()
}