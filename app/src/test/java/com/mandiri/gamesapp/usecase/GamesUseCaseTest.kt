package com.mandiri.gamesapp.usecase

import androidx.lifecycle.asLiveData
import com.mandiri.gamesapp.TestConstants
import com.mandiri.gamesapp.domain.Resource
import com.mandiri.gamesapp.domain.games.repository.IGamesRepository
import com.mandiri.gamesapp.domain.games.usecase.GamesUseCase
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GamesUseCaseTest {
    private lateinit var repository: IGamesRepository
    private lateinit var useCase: GamesUseCase

    @Before
    fun before() {
        repository = Mockito.mock(IGamesRepository::class.java)
        useCase = GamesUseCase(repository)
    }

    @Test
    fun test_GamesList_Return_Success() {
        // Given
        Mockito.`when`(repository.getGamesList()).thenReturn(flow {
            emit(
                Resource.Success(
                    TestConstants.gamesModel
                )
            )
        })
        // When
        useCase.getGamesList()
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesList()
        Assert.assertEquals(
            useCase.getGamesList().asLiveData().value?.data?.count,
            repository.getGamesList().asLiveData().value?.data?.count
        )
        Assert.assertEquals(
            useCase.getGamesList().asLiveData().value?.data?.results?.size,
            repository.getGamesList().asLiveData().value?.data?.results?.size
        )
        Assert.assertEquals(
            useCase.getGamesList().asLiveData().value?.data?.results?.first()?.released,
            repository.getGamesList().asLiveData().value?.data?.results?.first()?.released
        )
    }

    @Test
    fun test_GamesList_Return_Error() {
        // Given
        Mockito.`when`(repository.getGamesList()).thenReturn(flow {
            emit(Resource.Error(TestConstants.ERROR_MESSAGE))
        })
        // When
        useCase.getGamesList()
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesList()
        Assert.assertEquals(
            useCase.getGamesList().asLiveData().value?.message,
            repository.getGamesList().asLiveData().value?.message
        )
    }

    @Test
    fun test_GamesList_Return_Loading() {
        // Given
        Mockito.`when`(repository.getGamesList()).thenReturn(flow {
            emit(Resource.Loading())
        })
        // When
        useCase.getGamesList()
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesList()
        Assert.assertEquals(
            useCase.getGamesList().asLiveData().value,
            repository.getGamesList().asLiveData().value
        )
    }

    @Test
    fun test_GamesPaging_Return_Success() {
        // Given
        Mockito.`when`(repository.getGamesWithPaging(TestConstants.ONE)).thenReturn(flow {
            emit(
                Resource.Success(
                    TestConstants.gamesModel
                )
            )
        })
        // When
        useCase.getGamesWithPaging(TestConstants.ONE)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesWithPaging(TestConstants.ONE)
        Assert.assertEquals(
            useCase.getGamesWithPaging(TestConstants.ONE).asLiveData().value?.data?.count,
            repository.getGamesWithPaging(TestConstants.ONE).asLiveData().value?.data?.count
        )
        Assert.assertEquals(
            useCase.getGamesWithPaging(TestConstants.ONE).asLiveData().value?.data?.results?.size,
            repository.getGamesWithPaging(TestConstants.ONE).asLiveData().value?.data?.results?.size
        )
        Assert.assertEquals(
            useCase.getGamesWithPaging(TestConstants.ONE).asLiveData().value?.data?.results?.first()?.released,
            repository.getGamesWithPaging(TestConstants.ONE)
                .asLiveData().value?.data?.results?.first()?.released
        )
    }

    @Test
    fun test_GamesPaging_Return_Error() {
        // Given
        Mockito.`when`(repository.getGamesWithPaging(TestConstants.ONE)).thenReturn(flow {
            emit(Resource.Error(TestConstants.ERROR_MESSAGE))
        })
        // When
        useCase.getGamesWithPaging(TestConstants.ONE)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesWithPaging(TestConstants.ONE)
        Assert.assertEquals(
            useCase.getGamesWithPaging(TestConstants.ONE).asLiveData().value?.message,
            repository.getGamesWithPaging(TestConstants.ONE).asLiveData().value?.message
        )
    }

    @Test
    fun test_GamesPaging_Return_Loading() {
        // Given
        Mockito.`when`(repository.getGamesWithPaging(TestConstants.ONE)).thenReturn(flow {
            emit(Resource.Loading())
        })
        // When
        useCase.getGamesWithPaging(TestConstants.ONE)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesWithPaging(TestConstants.ONE)
        Assert.assertEquals(
            useCase.getGamesWithPaging(TestConstants.ONE).asLiveData().value,
            repository.getGamesWithPaging(TestConstants.ONE).asLiveData().value
        )
    }

    @Test
    fun test_GamesSearch_Return_Success() {
        // Given
        Mockito.`when`(repository.getSearchGames(TestConstants.ONE, TestConstants.NAME))
            .thenReturn(flow {
            emit(
                Resource.Success(
                    TestConstants.gamesModel
                )
            )
        })
        // When
        useCase.getSearchGames(TestConstants.ONE, TestConstants.NAME)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce())
            .getSearchGames(TestConstants.ONE, TestConstants.NAME)
        Assert.assertEquals(
            useCase.getSearchGames(TestConstants.ONE, TestConstants.NAME).asLiveData().value?.data?.count,
            repository.getSearchGames(TestConstants.ONE, TestConstants.NAME)
                .asLiveData().value?.data?.count
        )
        Assert.assertEquals(
            useCase.getSearchGames(
                TestConstants.ONE,
                TestConstants.NAME
            ).asLiveData().value?.data?.results?.size,
            repository.getSearchGames(TestConstants.ONE, TestConstants.NAME)
                .asLiveData().value?.data?.results?.size
        )
        Assert.assertEquals(
            useCase.getSearchGames(
                TestConstants.ONE,
                TestConstants.NAME
            ).asLiveData().value?.data?.results?.first()?.released,
            repository.getSearchGames(TestConstants.ONE, TestConstants.NAME)
                .asLiveData().value?.data?.results?.first()?.released
        )
    }

    @Test
    fun test_GamesSearch_Return_Error() {
        // Given
        Mockito.`when`(repository.getSearchGames(TestConstants.ONE, TestConstants.NAME))
            .thenReturn(flow {
            emit(Resource.Error(TestConstants.ERROR_MESSAGE))
        })
        // When
        useCase.getSearchGames(TestConstants.ONE, TestConstants.NAME)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce())
            .getSearchGames(TestConstants.ONE, TestConstants.NAME)
        Assert.assertEquals(
            useCase.getSearchGames(TestConstants.ONE, TestConstants.NAME).asLiveData().value?.message,
            repository.getSearchGames(TestConstants.ONE, TestConstants.NAME)
                .asLiveData().value?.message
        )
    }

    @Test
    fun test_GamesSearch_Return_Loading() {
        // Given
        Mockito.`when`(repository.getSearchGames(TestConstants.ONE, TestConstants.NAME))
            .thenReturn(flow {
            emit(Resource.Loading())
        })
        // When
        useCase.getSearchGames(TestConstants.ONE, TestConstants.NAME)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce())
            .getSearchGames(TestConstants.ONE, TestConstants.NAME)
        Assert.assertEquals(
            useCase.getSearchGames(TestConstants.ONE, TestConstants.NAME).asLiveData().value,
            repository.getSearchGames(TestConstants.ONE, TestConstants.NAME).asLiveData().value
        )
    }

    @Test
    fun test_GamesDetail_Return_Success() {
        // Given
        Mockito.`when`(repository.getGamesDetail(TestConstants.ONE)).thenReturn(flow {
            emit(
                Resource.Success(
                    TestConstants.gamesDetailModel
                )
            )
        })
        // When
        useCase.getGamesDetail(TestConstants.ONE)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesDetail(TestConstants.ONE)
        Assert.assertEquals(
            useCase.getGamesDetail(TestConstants.ONE).asLiveData().value?.data?.playtime,
            repository.getGamesDetail(TestConstants.ONE).asLiveData().value?.data?.playtime
        )
    }

    @Test
    fun test_GamesDetail_Return_Error() {
        // Given
        Mockito.`when`(repository.getGamesDetail(TestConstants.ONE)).thenReturn(flow {
            emit(Resource.Error(TestConstants.ERROR_MESSAGE))
        })
        // When
        useCase.getGamesDetail(TestConstants.ONE)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesDetail(TestConstants.ONE)
        Assert.assertEquals(
            useCase.getGamesDetail(TestConstants.ONE).asLiveData().value?.message,
            repository.getGamesDetail(TestConstants.ONE).asLiveData().value?.message
        )
    }

    @Test
    fun test_GamesDetail_Return_Loading() {
        // Given
        Mockito.`when`(repository.getGamesDetail(TestConstants.ONE)).thenReturn(flow {
            emit(Resource.Loading())
        })
        // When
        useCase.getGamesDetail(TestConstants.ONE)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGamesDetail(TestConstants.ONE)
        Assert.assertEquals(
            useCase.getGamesDetail(TestConstants.ONE).asLiveData().value,
            repository.getGamesDetail(TestConstants.ONE).asLiveData().value
        )
    }
    
}