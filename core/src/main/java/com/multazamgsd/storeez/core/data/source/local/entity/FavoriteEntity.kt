package com.multazamgsd.storeez.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.multazamgsd.storeez.core.domain.model.Story

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo("photo_url")
    val photoUrl: String? = null,

    @ColumnInfo("created_at")
    val createdAt: String? = null,

    @ColumnInfo("name")
    val name: String? = null,

    @ColumnInfo("description")
    val description: String? = null,

    @ColumnInfo("longitude")
    val lng: Double? = null,

    @ColumnInfo("latitude")
    val lat: Double? = null,

    var isFavorite: Boolean = true
) {
    fun toDomain() = Story(
        photoUrl = this.photoUrl ?: "",
        createdAt = this.createdAt ?: "",
        name = this.name ?: "",
        description = this.description ?: "",
        lng = this.lng ?: 0.0,
        id = this.id,
        lat = this.lat ?: 0.0,
        isFavorite = this.isFavorite
    )
}
