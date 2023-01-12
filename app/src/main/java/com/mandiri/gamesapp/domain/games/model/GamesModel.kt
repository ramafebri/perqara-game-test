package com.mandiri.gamesapp.domain.games.model

import com.mandiri.gamesapp.data.games.remote.response.GamesResponse
import com.mandiri.gamesapp.extension.orZero

data class GamesModel(
    val count: Int,
    val results: List<GameItemModel>
) {
    companion object {
        fun from(response: GamesResponse): GamesModel {
            return GamesModel(
                response.count.orZero(),
                results = GameItemModel.fromResponse(response.results)
            )
        }
    }
}
