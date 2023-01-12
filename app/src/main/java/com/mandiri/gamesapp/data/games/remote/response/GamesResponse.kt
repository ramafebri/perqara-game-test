package com.mandiri.gamesapp.data.games.remote.response

import com.google.gson.annotations.SerializedName

data class GamesResponse(
    @field:SerializedName("count")
    val count: Int? = null,
	@field:SerializedName("results")
    val results: List<GameItemResponse>? = null
)

data class GameItemResponse(
    @field:SerializedName("rating")
    val rating: Float? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("released")
    val released: String? = null,

    @field:SerializedName("background_image")
    val backgroundImage: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)
