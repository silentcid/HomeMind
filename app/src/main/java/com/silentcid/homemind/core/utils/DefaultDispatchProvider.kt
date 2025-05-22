package com.silentcid.homemind.core.utils

import com.silentcid.homemind.domain.models.infrastructure.coroutine.DefaultDispatcherProvider
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Provides
@Singleton
fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()