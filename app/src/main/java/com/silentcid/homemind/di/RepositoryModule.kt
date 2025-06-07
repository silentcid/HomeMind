package com.silentcid.homemind.di

import com.silentcid.homemind.repository.GroceryRepository
import com.silentcid.homemind.repository.GroceryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// Needed to bind the GroceryRepositoryImpl to the GroceryRepository interface
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindGroceryRepository(
        groceryRepositoryImpl: GroceryRepositoryImpl
    ): GroceryRepository

}