package com.multazamgsd.storeez.core.domain.model

import com.multazamgsd.storeez.core.data.source.local.entity.FavoriteEntity

data class Story(
    val photoUrl: String,
    val createdAt: String,
    val name: String,
    val description: String,
    val lng: Double,
    val id: String,
    val lat: Double,
    var isFavorite: Boolean,
) {
    fun toEntity() = FavoriteEntity(
        id = this.id,
        photoUrl = this.photoUrl,
        createdAt = this.createdAt,
        name = this.name,
        description = this.description,
        lat = this.lat,
        lng = this.lng,
        isFavorite = this.isFavorite
    )
}
