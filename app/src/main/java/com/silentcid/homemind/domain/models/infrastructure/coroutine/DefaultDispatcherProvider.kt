package com.silentcid.homemind.domain.models.infrastructure.coroutine

import com.silentcid.homemind.core.utils.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultDispatcherProvider: DispatcherProvider {
    override val Default: CoroutineDispatcher = Dispatchers.Default
    override val IO: CoroutineDispatcher = Dispatchers.IO
    override val Main: CoroutineDispatcher = Dispatchers.Main
    override val Unconfined: CoroutineDispatcher = Dispatchers.Unconfined

}