package com.multazamgsd.storeez.core.di

import android.content.Context
import androidx.room.Room
import com.multazamgsd.storeez.core.data.source.local.db.FavoriteDao
import com.multazamgsd.storeez.core.data.source.local.db.StoreezDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): StoreezDatabase = Room.databaseBuilder(
        context,
        StoreezDatabase::class.java, "Storeez.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideFavoriteDao(database: StoreezDatabase): FavoriteDao = database.favoriteDao()
}