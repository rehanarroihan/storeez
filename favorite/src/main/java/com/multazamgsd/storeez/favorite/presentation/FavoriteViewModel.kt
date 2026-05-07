package com.multazamgsd.storeez.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.multazamgsd.storeez.core.domain.model.Story
import com.multazamgsd.storeez.core.domain.usecase.StoryUseCase
import com.multazamgsd.storeez.core.utils.ResultState
import com.multazamgsd.storeez.core.utils.idle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val useCase: StoryUseCase
) : ViewModel() {

    private var _favorites: MutableStateFlow<ResultState<List<Story>>> = idle()
    val favorites: StateFlow<ResultState<List<Story>>> = _favorites

    private var _toggleFavoriteResult: MutableStateFlow<ResultState<Story>> = idle()
    val toggleFavoriteResult: StateFlow<ResultState<Story>> = _toggleFavoriteResult

    init {
        getFavorites()
    }

    fun getFavorites() {
        viewModelScope.launch {
            useCase.getFavorites().collect {
                _favorites.value = it
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