package com.mandiri.gamesapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.mandiri.gamesapp.TestConstants
import com.mandiri.gamesapp.TestConstants.ONE
import com.mandiri.gamesapp.domain.Resource
import com.mandiri.gamesapp.domain.favorite.usecase.IFavoriteUseCase
import com.mandiri.gamesapp.domain.games.usecase.IGamesUseCase
import com.mandiri.gamesapp.ui.detail.DetailViewModel
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DetailViewModelUnitTest {
    private lateinit var viewModel: DetailViewModel
    private lateinit var gamesUseCase: IGamesUseCase
    private lateinit var favoriteUseCase: IFavoriteUseCase

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        gamesUseCase = Mockito.mock(IGamesUseCase::class.java)
        favoriteUseCase = Mockito.mock(IFavoriteUseCase::class.java)
        viewModel = DetailViewModel(gamesUseCase, favoriteUseCase)
    }

    @Test
    fun test_GameId() {
        viewModel.gameId = TestConstants.TWO
        Assert.assertEquals(
            viewModel.gameId,
            TestConstants.TWO
        )
    }

    @Test
    fun test_GamesDetail() {
        viewModel.gameDetailModel = TestConstants.gamesDetailModel
        Assert.assertEquals(
            viewModel.gameDetailModel,
            TestConstants.gamesDetailModel
        )
    }

    @Test
    fun test_GameItem() {
        viewModel.gameItemModel = TestConstants.gameItemModel
        Assert.assertEquals(
            viewModel.gameItemModel,
            TestConstants.gameItemModel
        )
    }

    @Test
    fun test_IsFavorite() {
        viewModel.isFavorite = TestConstants.IS_FAVORITE
        Assert.assertEquals(
            viewModel.isFavorite,
            TestConstants.IS_FAVORITE
        )
    }

    @Test
    fun test_GamesDetail_Return_Success() {
        // Given
        Mockito.`when`(gamesUseCase.getGamesDetail(ONE)).thenReturn(flow {
            emit(
                Resource.Success(
                    TestConstants.gamesDetailModel
                )
            )
        })
        // When
        viewModel.getGamesDetail(ONE)
        // Then
        Mockito.verify(gamesUseCase, Mockito.atLeastOnce()).getGamesDetail(ONE)
        Assert.assertEquals(
            viewModel.getGamesDetail(ONE).value?.data?.playtime,
            gamesUseCase.getGamesDetail(ONE).asLiveData().value?.data?.playtime
        )
    }

    @Test
    fun test_GamesDetail_Return_Error() {
        // Given
        Mockito.`when`(gamesUseCase.getGamesDetail(ONE)).thenReturn(flow {
            emit(Resource.Error(TestConstants.ERROR_MESSAGE))
        })
        // When
        viewModel.getGamesDetail(ONE)
        // Then
        Mockito.verify(gamesUseCase, Mockito.atLeastOnce()).getGamesDetail(ONE)
        Assert.assertEquals(
            viewModel.getGamesDetail(ONE).value?.message,
            gamesUseCase.getGamesDetail(ONE).asLiveData().value?.message
        )
    }

    @Test
    fun test_GamesDetail_Return_Loading() {
        // Given
        Mockito.`when`(gamesUseCase.getGamesDetail(ONE)).thenReturn(flow {
            emit(Resource.Loading())
        })
        // When
        viewModel.getGamesDetail(ONE)
        // Then
        Mockito.verify(gamesUseCase, Mockito.atLeastOnce()).getGamesDetail(ONE)
        Assert.assertEquals(
            viewModel.getGamesDetail(ONE).value,
            gamesUseCase.getGamesDetail(ONE).asLiveData().value
        )
    }

    @Test
    fun test_AddGame_ToFavorite() {
        viewModel.addGameToFavorite(TestConstants.gamesDetailModel)
        // Then
        Mockito.verify(favoriteUseCase, Mockito.atLeastOnce())
            .addGameToFavorite(TestConstants.gamesDetailModel)
    }

    @Test
    fun test_DelGame_FromFavorite() {
        viewModel.deleteGameFromFavorite(TestConstants.gameItemModel)
        // Then
        Mockito.verify(favoriteUseCase, Mockito.atLeastOnce())
            .deleteGameFromFavorite(TestConstants.gameItemModel)
    }

    @Test
    fun test_GetGame_ById() {
        // Given
        Mockito.`when`(favoriteUseCase.getGameById(ONE)).thenReturn(flow {
            emit(TestConstants.gameItemModel)
        })
        // When
        viewModel.getGameByIdFromFavorite(ONE)
        // Then
        Mockito.verify(favoriteUseCase, Mockito.atLeastOnce()).getGameById(ONE)
        Assert.assertEquals(
            viewModel.getGameByIdFromFavorite(ONE).asLiveData().value?.gameId,
            favoriteUseCase.getGameById(ONE).asLiveData().value?.gameId
        )
    }

    @Test
    fun test_GetGame_ById_Null() {
        // Given
        Mockito.`when`(favoriteUseCase.getGameById(ONE)).thenReturn(flow {
            emit(null)
        })
        // When
        viewModel.getGameByIdFromFavorite(ONE)
        // Then
        Mockito.verify(favoriteUseCase, Mockito.atLeastOnce()).getGameById(ONE)
        Assert.assertEquals(
            viewModel.getGameByIdFromFavorite(ONE).asLiveData().value,
            favoriteUseCase.getGameById(ONE).asLiveData().value
        )
    }
}