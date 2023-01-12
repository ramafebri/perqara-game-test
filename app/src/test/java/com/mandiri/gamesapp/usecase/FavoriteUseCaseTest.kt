package com.mandiri.gamesapp.usecase

import androidx.lifecycle.asLiveData
import com.mandiri.gamesapp.TestConstants
import com.mandiri.gamesapp.domain.favorite.repository.IFavoriteRepository
import com.mandiri.gamesapp.domain.favorite.usecase.FavoriteUseCase
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class FavoriteUseCaseTest {
    private lateinit var repository: IFavoriteRepository
    private lateinit var useCase: FavoriteUseCase

    @Before
    fun before() {
        repository = Mockito.mock(IFavoriteRepository::class.java)
        useCase = FavoriteUseCase(repository)
    }

    @Test
    fun test_AddGame_ToFavorite() {
        useCase.addGameToFavorite(TestConstants.gamesDetailModel)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce())
            .addGameToFavorite(TestConstants.gamesDetailModel)
    }

    @Test
    fun test_DelGame_FromFavorite() {
        useCase.deleteGameFromFavorite(TestConstants.gameItemModel)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce())
            .deleteGameFromFavorite(TestConstants.gameItemModel)
    }

    @Test
    fun test_GetGame_ById() {
        // Given
        Mockito.`when`(repository.getGameById(TestConstants.ONE)).thenReturn(flow {
            emit(TestConstants.gameItemModel)
        })
        // When
        useCase.getGameById(TestConstants.ONE)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGameById(TestConstants.ONE)
        Assert.assertEquals(
            useCase.getGameById(TestConstants.ONE).asLiveData().value?.gameId,
            repository.getGameById(TestConstants.ONE).asLiveData().value?.gameId
        )
    }

    @Test
    fun test_GetGame_ById_Null() {
        // Given
        Mockito.`when`(repository.getGameById(TestConstants.ONE)).thenReturn(flow {
            emit(null)
        })
        // When
        useCase.getGameById(TestConstants.ONE)
        // Then
        Mockito.verify(repository, Mockito.atLeastOnce()).getGameById(TestConstants.ONE)
        Assert.assertEquals(
            useCase.getGameById(TestConstants.ONE).asLiveData().value,
            repository.getGameById(TestConstants.ONE).asLiveData().value
        )
    }
    
}