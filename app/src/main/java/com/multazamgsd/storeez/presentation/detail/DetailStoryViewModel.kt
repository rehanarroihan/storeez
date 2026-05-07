package com.multazamgsd.storeez.presentation.detail

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
class DetailStoryViewModel @Inject constructor(
    private val useCase: StoryUseCase
) : ViewModel() {

    private var _toggleFavoriteResult: MutableStateFlow<ResultState<Story>> = idle()
    val toggleFavoriteResult: StateFlow<ResultState<Story>> = _toggleFavoriteResult

    fun toggleFavorite(story: Story) {
        viewModelScope.launch {
            useCase.toggleFavorite(story).collect {
                _toggleFavoriteResult.value = it
            }
        }
    }
}