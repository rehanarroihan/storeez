package com.multazamgsd.storeez.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.multazamgsd.storeez.core.data.source.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class StoreezDatabase: RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}