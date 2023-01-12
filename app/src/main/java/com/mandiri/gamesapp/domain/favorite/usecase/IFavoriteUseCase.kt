package com.mandiri.gamesapp.domain.favorite.usecase

import com.mandiri.gamesapp.domain.favorite.repository.GamesPagingSource
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import kotlinx.coroutines.flow.Flow

interface IFavoriteUseCase {
    fun gamesPagingSource(): GamesPagingSource
    fun addGameToFavorite(data: GamesDetailModel)
    fun deleteGameFromFavorite(data: GameItemModel)
    fun getGameById(movieId: Int): Flow<GameItemModel?>
}