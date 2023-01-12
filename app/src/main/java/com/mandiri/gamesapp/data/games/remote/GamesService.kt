package com.mandiri.gamesapp.data.games.remote

import com.mandiri.gamesapp.common.ApiConstants
import com.mandiri.gamesapp.data.games.remote.response.GamesDetailResponse
import com.mandiri.gamesapp.data.games.remote.response.GamesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesService {
    @GET(ApiConstants.GET_GAMES_URL)
    suspend fun getGamesList(): Response<GamesResponse>

    @GET(ApiConstants.GET_GAMES_URL)
    suspend fun getGamesWithPaging(
        @Query(ApiConstants.PAGE) page: Int,
        @Query(ApiConstants.PAGE_SIZE) pageSize: Int = ApiConstants.DEFAULT_PAGE_SIZE
    ): Response<GamesResponse>

    @GET(ApiConstants.GET_GAMES_URL)
    suspend fun getSearchGames(
        @Query(ApiConstants.PAGE) page: Int,
        @Query(ApiConstants.SEARCH) search: String
    ): Response<GamesResponse>

    @GET(ApiConstants.GET_GAMES_DETAIL_URL)
    suspend fun getGamesDetail(
        @Path(ApiConstants.GAME_ID) gameId: Int
    ): Response<GamesDetailResponse>
}