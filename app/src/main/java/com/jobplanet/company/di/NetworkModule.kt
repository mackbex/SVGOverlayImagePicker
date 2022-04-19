package com.jobplanet.company.di

import com.google.gson.GsonBuilder
import com.jobplanet.company.BuildConfig
import com.jobplanet.company.data.source.remote.NetConstants
import com.jobplanet.company.data.source.remote.NetConstants.CELL_TYPE_COMPANY
import com.jobplanet.company.data.source.remote.NetConstants.CELL_TYPE_FIELD
import com.jobplanet.company.data.source.remote.NetConstants.CELL_TYPE_HORIZONTAL_THEME
import com.jobplanet.company.data.source.remote.NetConstants.CELL_TYPE_REVIEW
import com.jobplanet.company.data.source.remote.service.CompanySearchService
import com.jobplanet.company.domain.model.Company
import com.jobplanet.company.domain.model.HorizontalTheme
import com.jobplanet.company.domain.model.Items
import com.jobplanet.company.domain.model.Review
import com.jobplanet.company.util.RuntimeTypeAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = NetConstants.BASE_URL_V1

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(Level.BODY)
            client.addInterceptor(logging)
        }

        return client.build()
    }

    /**
     * Gson을 통한 JSON 데이터 파싱. CELL_TYPE 구분을 위한 RuntimeTypeAdapterFactory 추가.
     */
    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {

        val adapter = RuntimeTypeAdapterFactory
            .of(Items::class.java, CELL_TYPE_FIELD, true)
            .registerSubtype(Company::class.java, CELL_TYPE_COMPANY)
            .registerSubtype(HorizontalTheme::class.java, CELL_TYPE_HORIZONTAL_THEME)
            .registerSubtype(Review::class.java, CELL_TYPE_REVIEW)

        return GsonConverterFactory.create(GsonBuilder().registerTypeAdapterFactory(adapter).create())
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String, gsonConverterFactory: GsonConverterFactory) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCompanySearchService(retrofit: Retrofit): CompanySearchService {
        return retrofit.create(CompanySearchService::class.java)
    }
}