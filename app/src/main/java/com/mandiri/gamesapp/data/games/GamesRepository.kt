package com.mandiri.gamesapp.data.games

import com.mandiri.gamesapp.data.base.response.ApiResponse
import com.mandiri.gamesapp.data.games.remote.IGamesRemoteSource
import com.mandiri.gamesapp.domain.Resource
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import com.mandiri.gamesapp.domain.games.model.GamesModel
import com.mandiri.gamesapp.domain.games.repository.IGamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesRepository @Inject constructor(private val remoteDataSource: IGamesRemoteSource) :
    IGamesRepository {
    override fun getGamesList(): Flow<Resource<GamesModel>> {
        return callApi(
            apiFunction = {
                remoteDataSource.getGamesList()
            },
            mapper = {
                GamesModel.from(it)
            }
        )
    }

    override fun getGamesWithPaging(page: Int): Flow<Resource<GamesModel>> {
        return callApi(
            apiFunction = {
                remoteDataSource.getGamesWithPaging(page)
            },
            mapper = {
                GamesModel.from(it)
            }
        )
    }

    override fun getSearchGames(page:Int, search: String): Flow<Resource<GamesModel>> {
        return callApi(
            apiFunction = {
                remoteDataSource.getSearchGames(page, search)
            },
            mapper = {
                GamesModel.from(it)
            }
        )
    }

    override fun getGamesDetail(gameId: Int): Flow<Resource<GamesDetailModel>> {
        return callApi(
            apiFunction = {
                remoteDataSource.getGamesDetail(gameId)
            },
            mapper = {
                GamesDetailModel.from(it)
            }
        )
    }

    @TestOnly
    fun <T, R> callApi(
        apiFunction: suspend () -> Flow<ApiResponse<R>>,
        mapper: (R) -> T
    ): Flow<Resource<T>> {
        return flow {
            emit(Resource.Loading())
            when (val response = apiFunction.invoke().single()) {
                is ApiResponse.Success -> {
                    emit(
                        Resource.Success(
                            mapper.invoke(response.data)
                        )
                    )
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.throwable.message.orEmpty()))
                }
            }
        }
    }
}