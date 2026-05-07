package com.multazamgsd.storeez.core.domain.repository

import com.multazamgsd.storeez.core.data.source.local.entity.FavoriteEntity
import com.multazamgsd.storeez.core.data.source.remote.response.StoriesResponse
import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface StoryRepository {

    fun getStories(): Flow<ResultState<List<Story>>>

    fun toggleFavorite(story: Story): Flow<ResultState<Story>>

    fun getFavorites(): Flow<ResultState<List<Story>>>
}