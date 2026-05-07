package com.multazamgsd.storeez.core.data.source.remote.network

import com.multazamgsd.storeez.core.data.source.remote.response.StoriesResponse
import retrofit2.Response
import retrofit2.http.*

interface StoryService {

    @GET("stories")
    suspend fun getStoryList(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 15,
        @Query("location") withLocation: Int = 1
    ): Response<StoriesResponse>
}