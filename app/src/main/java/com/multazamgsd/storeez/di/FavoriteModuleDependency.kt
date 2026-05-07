package com.multazamgsd.storeez.di

import com.multazamgsd.storeez.core.domain.usecase.StoryUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependency {

    fun storyUseCase(): StoryUseCase
}