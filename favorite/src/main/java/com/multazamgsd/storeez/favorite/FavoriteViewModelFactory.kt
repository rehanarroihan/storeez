package com.multazamgsd.storeez.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.multazamgsd.storeez.core.domain.usecase.StoryUseCase
import com.multazamgsd.storeez.favorite.presentation.FavoriteViewModel
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    private val useCase: StoryUseCase
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(FavoriteViewModel::class.java)->{
                FavoriteViewModel(useCase) as T
            }
            else->{
                throw Throwable("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }
}