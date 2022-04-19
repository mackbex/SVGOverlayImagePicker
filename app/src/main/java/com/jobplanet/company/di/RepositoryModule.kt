package com.jobplanet.company.di

import com.jobplanet.company.data.repository.CompanyInfoRepositoryImpl
import com.jobplanet.company.domain.repository.CompanyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {


    @Binds
    fun bindCompanyRepository(impl : CompanyInfoRepositoryImpl): CompanyRepository
}