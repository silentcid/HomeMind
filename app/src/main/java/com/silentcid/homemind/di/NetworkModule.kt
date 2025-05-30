package com.silentcid.homemind.di

import com.silentcid.homemind.core.retrofit.ResultRetrofitAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  NetworkModule {

    //Gemini Base URL
    private const val GEMINI_BASE_URL = "https://generativelanguage.googleapis.com/"
    private const val N8N_BASE_URL = "http://TO_BE_ADDED_LATER.com"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    @Named("GeminiService")
    fun provideGeminiRetrofit(moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(GEMINI_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ResultRetrofitAdapterFactory())
            .build()



}