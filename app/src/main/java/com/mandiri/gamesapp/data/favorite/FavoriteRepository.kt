package com.mandiri.gamesapp.data.favorite

import com.mandiri.gamesapp.data.favorite.local.GamesDao
import com.mandiri.gamesapp.data.favorite.local.GamesEntity
import com.mandiri.gamesapp.domain.favorite.repository.GamesPagingSource
import com.mandiri.gamesapp.domain.favorite.repository.IFavoriteRepository
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(private val gamesDao: GamesDao) : IFavoriteRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun gamesPagingSource() = GamesPagingSource(gamesDao)

    override fun addGameToFavorite(data: GamesDetailModel) {
        coroutineScope.launch {
            gamesDao.insert(
                GamesEntity.fromDetail(data)
            )
        }
    }

    override fun deleteGameFromFavorite(data: GameItemModel) {
        coroutineScope.launch {
            gamesDao.delete(
                GamesEntity.fromItem(data)
            )
        }
    }

    override fun getGameById(movieId: Int): Flow<GameItemModel?> {
        return flow {
            gamesDao.getGameById(movieId).collect {
                emit(GameItemModel.fromEntity(it))
            }
        }
    }
}