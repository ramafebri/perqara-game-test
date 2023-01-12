package com.mandiri.gamesapp.domain.games.usecase

import com.mandiri.gamesapp.domain.Resource
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import com.mandiri.gamesapp.domain.games.model.GamesModel
import kotlinx.coroutines.flow.Flow

interface IGamesUseCase {
    fun getGamesList(): Flow<Resource<GamesModel>>
    fun getGamesWithPaging(page: Int): Flow<Resource<GamesModel>>
    fun getSearchGames(page: Int, search: String): Flow<Resource<GamesModel>>
    fun getGamesDetail(gameId: Int): Flow<Resource<GamesDetailModel>>
}