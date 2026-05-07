package com.multazamgsd.storeez.core.domain.usecase

import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {

    fun getStories(): Flow<ResultState<List<Story>>>

    fun toggleFavorite(story: Story): Flow<ResultState<Story>>

    fun getFavorites(): Flow<ResultState<List<Story>>>
}