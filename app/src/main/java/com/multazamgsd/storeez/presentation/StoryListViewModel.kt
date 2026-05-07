package com.multazamgsd.storeez.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.domain.usecase.StoryUseCase
import com.multazamgsd.storeez.core.utils.ResultState
import com.multazamgsd.storeez.core.utils.idle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoryListViewModel @Inject constructor(
    private val useCase: StoryUseCase
) : ViewModel() {

    private var _stories: MutableStateFlow<ResultState<List<Story>>> = idle()
    val stories: StateFlow<ResultState<List<Story>>> = _stories

    private var _toggleFavoriteResult: MutableStateFlow<ResultState<Story>> = idle()
    val toggleFavoriteResult: StateFlow<ResultState<Story>> = _toggleFavoriteResult

    init {
        getStories()
    }

    fun getStories() {
        viewModelScope.launch {
            useCase.getStories().collect {
                _stories.value = it
            }
        }
    }

    fun toggleFavorite(story: Story) {
        viewModelScope.launch {
            useCase.toggleFavorite(story).collect {
                _toggleFavoriteResult.value = it
            }
        }
    }
}