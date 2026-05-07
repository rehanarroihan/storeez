package com.multazamgsd.storeez.di

import com.multazamgsd.storeez.core.domain.usecase.StoryUseCase
import com.multazamgsd.storeez.core.domain.usecase.StoryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideStoryUseCase(useCase: StoryUseCaseImpl): StoryUseCase
}