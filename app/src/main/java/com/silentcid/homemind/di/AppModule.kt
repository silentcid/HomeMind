package com.silentcid.homemind.di

import com.silentcid.homemind.core.utils.DispatcherProvider
import com.silentcid.homemind.domain.models.infrastructure.coroutine.DefaultDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideDispatcherProvider():DispatcherProvider = DefaultDispatcherProvider()



}