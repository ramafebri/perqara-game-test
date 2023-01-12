package com.mandiri.gamesapp.common

object ApiConstants {
    private const val SLASH = "/"
    private const val GAMES = "games"
    const val PAGE = "page"
    const val DEFAULT_PAGE = 1
    const val DEFAULT_PAGE_SIZE = 20
    const val PAGE_SIZE = "page_size"
    const val SEARCH = "search"
    const val GAME_ID = "gameId"
    const val GET_GAMES_URL = GAMES
    const val GET_GAMES_DETAIL_URL = "$GAMES$SLASH{$GAME_ID}"
}