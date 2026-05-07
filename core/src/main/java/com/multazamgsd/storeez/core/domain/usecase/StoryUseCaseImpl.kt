package com.multazamgsd.storeez.core.domain.usecase

import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.domain.repository.StoryRepository
import com.multazamgsd.storeez.core.utils.ResultState
import com.multazamgsd.storeez.core.utils.mapResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoryUseCaseImpl @Inject constructor(
    private val repository: StoryRepository
) : StoryUseCase {

    override fun getStories(): Flow<ResultState<List<Story>>> = repository.getStories()

    override fun toggleFavorite(story: Story): Flow<ResultState<Story>> = repository.toggleFavorite(story)

    override fun getFavorites(): Flow<ResultState<List<Story>>> = repository.getFavorites()
}