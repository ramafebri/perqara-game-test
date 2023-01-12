package com.mandiri.gamesapp.domain.games.usecase

import com.mandiri.gamesapp.domain.Resource
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import com.mandiri.gamesapp.domain.games.model.GamesModel
import com.mandiri.gamesapp.domain.games.repository.IGamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesUseCase @Inject constructor(private val repository: IGamesRepository) :
    IGamesUseCase {
    override fun getGamesList(): Flow<Resource<GamesModel>> {
        return repository.getGamesList()
    }

    override fun getGamesWithPaging(page: Int): Flow<Resource<GamesModel>> {
        return repository.getGamesWithPaging(page)
    }

    override fun getSearchGames(page: Int, search: String): Flow<Resource<GamesModel>> {
        return repository.getSearchGames(page, search)
    }

    override fun getGamesDetail(gameId: Int): Flow<Resource<GamesDetailModel>> {
        return repository.getGamesDetail(gameId)
    }
}