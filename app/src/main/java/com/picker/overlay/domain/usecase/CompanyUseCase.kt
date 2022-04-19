package com.picker.overlay.domain.usecase

import com.picker.overlay.domain.repository.CompanyRepository
import javax.inject.Inject


class CompanyUseCase @Inject constructor(
    private val companyRepository: CompanyRepository
) {
    suspend fun getSearchResult() = companyRepository.getCompanyList()
}