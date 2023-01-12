package com.mandiri.gamesapp

import com.mandiri.gamesapp.data.favorite.local.GamesEntity
import com.mandiri.gamesapp.data.games.remote.response.GameItemResponse
import com.mandiri.gamesapp.data.games.remote.response.GamesDetailResponse
import com.mandiri.gamesapp.data.games.remote.response.GamesResponse
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import com.mandiri.gamesapp.domain.games.model.GamesModel

object TestConstants {
    const val ZERO = 0
    const val ONE = 1
    const val TWO = 2
    const val SLASH = "/"
    const val IS_FAVORITE = true
    const val TRUE = true
    private const val RATING = 1.1f
    private const val PLAYTIME = 10
    private const val DESCRIPTION = "DESCRIPTION"
    private const val RELEASED = "10-10-2010"
    private const val BACKGROUND = "background"
    const val NAME = "avatar"
    const val ERROR_MESSAGE = "ERROR_MESSAGE"
    const val DEFAULT_ERROR_MESSAGE = "Terjadi kesalah sistem"
    val THROWABLE = Throwable(ERROR_MESSAGE)
    val gameItemModel = GameItemModel(
        RATING,
        ONE,
        RELEASED,
        BACKGROUND,
        NAME,
        ZERO
    )
    val gameItemModelList = listOf(gameItemModel)
    val gamesModel = GamesModel(
        count = ONE,
        results = listOf(gameItemModel)
    )
    val gamesDetailModel = GamesDetailModel(
        RATING,
        PLAYTIME,
        ONE,
        RELEASED,
        DESCRIPTION,
        BACKGROUND,
        NAME
    )
    val gamesEntity = GamesEntity(
        rating = RATING,
        gameId = ONE,
        released = RELEASED,
        backgroundImage = BACKGROUND,
        name = NAME,
        localGameId = ZERO
    )
    val gamesEntityList = listOf(gamesEntity)
    private val gameItemResponse = GameItemResponse(
        RATING,
        ONE,
        RELEASED,
        BACKGROUND,
        NAME
    )
    val gamesResponse = GamesResponse(
        count = ONE,
        results = listOf(gameItemResponse)
    )
    val gamesDetailResponse = GamesDetailResponse(
        RATING,
        PLAYTIME,
        ONE,
        RELEASED,
        DESCRIPTION,
        BACKGROUND,
        NAME
    )
}