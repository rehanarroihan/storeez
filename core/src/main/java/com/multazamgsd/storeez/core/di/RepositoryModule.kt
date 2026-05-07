package com.multazamgsd.storeez.core.di

import com.multazamgsd.storeez.core.domain.repository.StoryRepository
import com.multazamgsd.storeez.core.data.StoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideStoryRepository(repository: StoryRepositoryImpl): StoryRepository
}