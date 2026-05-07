package com.multazamgsd.storeez.favorite

import android.content.Context
import com.multazamgsd.storeez.di.FavoriteModuleDependency
import com.multazamgsd.storeez.favorite.presentation.FavoriteActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component(dependencies = [FavoriteModuleDependency::class])
interface FavoriteComponent {

    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependency): Builder
        fun build(): FavoriteComponent
    }
}
