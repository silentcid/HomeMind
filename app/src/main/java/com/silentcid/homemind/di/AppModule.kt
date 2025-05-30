package com.silentcid.homemind.di

import com.silentcid.homemind.core.utils.DispatcherProvider
import com.silentcid.homemind.domain.models.infrastructure.coroutine.DefaultDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDispatcherProvider():DispatcherProvider = DefaultDispatcherProvider()



}