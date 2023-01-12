package com.mandiri.gamesapp.data.games.remote

import com.mandiri.gamesapp.data.base.common.BaseServiceAPI
import com.mandiri.gamesapp.data.base.response.ApiResponse
import com.mandiri.gamesapp.data.games.remote.response.GamesDetailResponse
import com.mandiri.gamesapp.data.games.remote.response.GamesResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesRemoteSource @Inject constructor(private val apiService: GamesService) :
    BaseServiceAPI(),
    IGamesRemoteSource {
    override suspend fun getGamesList(): Flow<ApiResponse<GamesResponse>> {
        return callApi {
            apiService.getGamesList()
        }
    }

    override suspend fun getGamesWithPaging(page: Int): Flow<ApiResponse<GamesResponse>> {
        return callApi {
            apiService.getGamesWithPaging(page)
        }
    }

    override suspend fun getSearchGames(page: Int,search: String): Flow<ApiResponse<GamesResponse>> {
        return callApi {
            apiService.getSearchGames(page, search)
        }
    }

    override suspend fun getGamesDetail(gameId: Int): Flow<ApiResponse<GamesDetailResponse>> {
        return callApi {
            apiService.getGamesDetail(gameId)
        }
    }
}