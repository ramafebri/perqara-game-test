package com.mandiri.gamesapp.data.games.remote.response

import com.google.gson.annotations.SerializedName

data class GamesDetailResponse(
    @field:SerializedName("rating")
    val rating: Float? = null,

    @field:SerializedName("playtime")
    val playtime: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("released")
    val released: String? = null,

    @field:SerializedName("description_raw")
    val descriptionRaw: String? = null,

    @field:SerializedName("background_image")
    val backgroundImage: String? = null,

    @field:SerializedName("name")
    val name: String? = null
)

