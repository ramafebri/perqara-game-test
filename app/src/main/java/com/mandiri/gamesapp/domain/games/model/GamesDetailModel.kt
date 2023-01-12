package com.mandiri.gamesapp.domain.games.model

import com.mandiri.gamesapp.data.games.remote.response.GamesDetailResponse
import com.mandiri.gamesapp.extension.orZero

data class GamesDetailModel(
    val rating: Float,
    val playtime: Int,
    val gameId: Int,
    val released: String,
    val descriptionRaw: String,
    val backgroundImage: String,
    val name: String
) {
    companion object {
        fun from(response: GamesDetailResponse): GamesDetailModel {
            return GamesDetailModel(
                response.rating.orZero(),
                response.playtime.orZero(),
                response.id.orZero(),
                response.released.orEmpty(),
                response.descriptionRaw.orEmpty(),
                response.backgroundImage.orEmpty(),
                response.name.orEmpty()
            )
        }
    }
}
