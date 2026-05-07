package com.multazamgsd.storeez.core.di

import com.multazamgsd.storeez.core.data.source.remote.network.StoryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val BASE_URL = "https://story-api.dicoding.dev/v1/"
        const val AUTH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTlRYS1zWlBKN0o4QzlkbDIiLCJpYXQiOjE3NzgxMzUzNTh9.EmeUvbF33BEr_hDT7Stm60EjNnCmObz3NIdpzupr3FU"
    }

    @Provides
    @Singleton
    fun providesHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer $AUTH_TOKEN"
                    )
                    .build()
            )
        }
        .build()

    @Provides
    @Singleton
    fun providesStoryService(okHttpClient: OkHttpClient): StoryService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(StoryService::class.java)
}