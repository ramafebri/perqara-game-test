package com.mandiri.gamesapp.domain.favorite.usecase

import com.mandiri.gamesapp.domain.favorite.repository.GamesPagingSource
import com.mandiri.gamesapp.domain.favorite.repository.IFavoriteRepository
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteUseCase @Inject constructor(private val repository: IFavoriteRepository) :
    IFavoriteUseCase {
    override fun gamesPagingSource(): GamesPagingSource {
        return repository.gamesPagingSource()
    }

    override fun addGameToFavorite(data: GamesDetailModel) {
        return repository.addGameToFavorite(data)
    }

    override fun deleteGameFromFavorite(data: GameItemModel) {
        return repository.deleteGameFromFavorite(data)
    }

    override fun getGameById(movieId: Int): Flow<GameItemModel?> {
        return repository.getGameById(movieId)
    }
}