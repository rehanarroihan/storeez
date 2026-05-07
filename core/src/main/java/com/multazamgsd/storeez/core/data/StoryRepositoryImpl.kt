package com.multazamgsd.storeez.core.data

import com.multazamgsd.storeez.core.data.source.local.db.FavoriteDao
import com.multazamgsd.storeez.core.data.source.local.entity.FavoriteEntity
import com.multazamgsd.storeez.core.data.source.remote.network.StoryService
import com.multazamgsd.storeez.core.data.source.remote.response.StoriesResponse
import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.domain.repository.StoryRepository
import com.multazamgsd.storeez.core.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val service: StoryService,
    private val dao: FavoriteDao
) : StoryRepository {

    override fun getStories(): Flow<ResultState<List<Story>>> {
        return flow {
            emit(ResultState.Loading())

            val dataResponse = service.getStoryList()
            if (dataResponse.isSuccessful) {
                val storyList = dataResponse.body()?.stories
                val favoriteList = dao.getFavorites()
                storyList?.forEach { storyItem ->
                    favoriteList.forEach { favItem ->
                        if (storyItem.id == favItem.id) {
                            storyItem.isFavorite = true
                        }
                    }
                }
                emit(ResultState.Success(storyList?.map { it.toDomain() }))
            } else {
                emit(ResultState.Error((dataResponse.errorBody().toString())))
            }
        }.catch { error ->
            error.printStackTrace()
            emit(ResultState.Error(error.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override fun toggleFavorite(story: Story): Flow<ResultState<Story>> {
        return flow {
            emit(ResultState.Loading())

            if (!story.isFavorite) {
                story.isFavorite = true
                dao.insertFavorite(story.toEntity())
            } else {
                story.isFavorite = false
                dao.deleteFavorite(story.id)
            }

            emit(ResultState.Success(story))
        }.catch {error ->
            error.printStackTrace()
            emit(ResultState.Error(error.message.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavorites(): Flow<ResultState<List<Story>>> {
        return flow {
            emit(ResultState.Loading())

            val result = dao.getFavorites()

            emit(ResultState.Success(result.map { it.toDomain() }))
        }.catch { error ->
            error.printStackTrace()
            emit(ResultState.Error(error.message.toString()))
        }.flowOn(Dispatchers.IO)
    }
}