package com.multazamgsd.storeez.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.multazamgsd.storeez.core.data.source.local.entity.FavoriteEntity
import com.multazamgsd.storeez.core.domain.model.Story

data class StoriesResponse(
	@field:SerializedName("listStory")
	val stories: List<StoryResponse>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) {
	data class StoryResponse(

		@field:SerializedName("photoUrl")
		val photoUrl: String? = null,

		@field:SerializedName("createdAt")
		val createdAt: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("description")
		val description: String? = null,

		@field:SerializedName("lon")
		val lng: Double? = null,

		@field:SerializedName("id")
		val id: String? = null,

		@field:SerializedName("lat")
		val lat: Double? = null,

		var isFavorite: Boolean = false
	) {
		fun toDomain() = Story(
			photoUrl = this.photoUrl ?: "",
			createdAt = this.createdAt ?: "",
			name = this.name ?: "",
			description = this.description ?: "",
			lng = this.lng ?: 0.0,
			id = this.id!!,
			lat = this.lat ?: 0.0,
			isFavorite = isFavorite
		)

		fun toEntity() = FavoriteEntity(
			photoUrl = this.photoUrl,
			createdAt = this.createdAt,
			name = this.name,
			description = this.description,
			lng = this.lng,
			lat = this.lat,
			id = this.id.toString()
		)
	}
}
