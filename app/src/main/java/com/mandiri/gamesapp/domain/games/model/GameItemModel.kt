package com.mandiri.gamesapp.domain.games.model

import com.mandiri.gamesapp.common.Constants.ZERO
import com.mandiri.gamesapp.data.favorite.local.GamesEntity
import com.mandiri.gamesapp.data.games.remote.response.GameItemResponse
import com.mandiri.gamesapp.extension.orZero

data class GameItemModel(
    val rating: Float,
    val gameId: Int,
    val released: String,
    val backgroundImage: String,
    val name: String,
    val localGameId: Int = ZERO
) {
    companion object {
        fun fromResponse(response: List<GameItemResponse>?): List<GameItemModel> {
            return response?.map {
                GameItemModel(
                    it.rating.orZero(),
                    it.id.orZero(),
                    it.released.orEmpty(),
                    it.backgroundImage.orEmpty(),
                    it.name.orEmpty()
                )
            }.orEmpty()
        }

        fun fromListEntity(response: List<GamesEntity>): List<GameItemModel> {
            return response.map {
                GameItemModel(
                    it.rating.orZero(),
                    it.gameId.orZero(),
                    it.released.orEmpty(),
                    it.backgroundImage.orEmpty(),
                    it.name.orEmpty(),
                    it.localGameId
                )
            }
        }

        fun fromEntity(response: GamesEntity?): GameItemModel? {
            return if (response == null) {
                null
            } else {
                GameItemModel(
                    response.rating.orZero(),
                    response.gameId.orZero(),
                    response.released.orEmpty(),
                    response.backgroundImage.orEmpty(),
                    response.name.orEmpty(),
                    response.localGameId
                )
            }
        }
    }
}
