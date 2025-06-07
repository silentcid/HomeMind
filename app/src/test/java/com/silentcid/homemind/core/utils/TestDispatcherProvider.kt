package com.silentcid.homemind.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider(
    val testDispatcher: TestDispatcher = StandardTestDispatcher() // Use StandardTestDispatcher
) : DispatcherProvider {
    override val Main: CoroutineDispatcher
        get() = testDispatcher
    override val IO: CoroutineDispatcher
        get() = testDispatcher
    override val Default: CoroutineDispatcher
        get() = testDispatcher
    override val Unconfined: CoroutineDispatcher
        get() = testDispatcher
}