package com.multazamgsd.storeez.core.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.multazamgsd.storeez.core.data.source.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(stories: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    suspend fun getFavorites(): List<FavoriteEntity>

    @Query("SELECT * from favorite WHERE id = :id")
    suspend fun getFavorite(id: String): FavoriteEntity?

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun deleteFavorite(id: String)
}