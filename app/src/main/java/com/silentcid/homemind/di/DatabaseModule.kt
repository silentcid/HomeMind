package com.silentcid.homemind.di

import android.content.Context
import androidx.room.Room
import com.silentcid.homemind.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "groceryItem.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGroceryDao(appDatabase: AppDatabase) = appDatabase.groceryDao()
}

