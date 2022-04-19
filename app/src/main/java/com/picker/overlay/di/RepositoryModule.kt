package com.picker.overlay.di

import com.picker.overlay.data.repository.CompanyInfoRepositoryImpl
import com.picker.overlay.domain.repository.CompanyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {


    @Binds
    fun bindCompanyRepository(impl : CompanyInfoRepositoryImpl): CompanyRepository
}