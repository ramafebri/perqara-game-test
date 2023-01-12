package com.mandiri.gamesapp.data.games.remote

import com.mandiri.gamesapp.data.base.response.ApiResponse
import com.mandiri.gamesapp.data.games.remote.response.GamesDetailResponse
import com.mandiri.gamesapp.data.games.remote.response.GamesResponse
import kotlinx.coroutines.flow.Flow

interface IGamesRemoteSource {
    suspend fun getGamesList(): Flow<ApiResponse<GamesResponse>>
    suspend fun getGamesWithPaging(page: Int): Flow<ApiResponse<GamesResponse>>
    suspend fun getSearchGames(page: Int, search: String): Flow<ApiResponse<GamesResponse>>
    suspend fun getGamesDetail(gameId: Int): Flow<ApiResponse<GamesDetailResponse>>
}