package com.picker.overlay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IODispatcher

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainDispatcher

    @Provides
    @IODispatcher
    fun provideIoDispatcher() = Dispatchers.IO


    @Provides
    @MainDispatcher
    fun provideMainDispatcher() = Dispatchers.Main
}