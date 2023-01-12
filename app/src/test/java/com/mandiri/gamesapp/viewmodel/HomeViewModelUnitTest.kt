package com.mandiri.gamesapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.mandiri.gamesapp.TestConstants
import com.mandiri.gamesapp.TestConstants.ERROR_MESSAGE
import com.mandiri.gamesapp.TestConstants.NAME
import com.mandiri.gamesapp.TestConstants.ONE
import com.mandiri.gamesapp.TestConstants.TWO
import com.mandiri.gamesapp.domain.Resource
import com.mandiri.gamesapp.domain.games.usecase.IGamesUseCase
import com.mandiri.gamesapp.ui.home.HomeViewModel
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class HomeViewModelUnitTest {
    private lateinit var viewModel: HomeViewModel
    private lateinit var useCase: IGamesUseCase

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        useCase = mock(IGamesUseCase::class.java)
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun test_CurrentPage() {
        viewModel.currentPage = TWO
        assertEquals(
            viewModel.currentPage,
            TWO
        )
    }

    @Test
    fun test_CurrentSearch() {
        viewModel.currentSearch = NAME
        assertEquals(
            viewModel.currentSearch,
            NAME
        )
    }

    @Test
    fun test_GamesList_Return_Success() {
        // Given
        `when`(useCase.getGamesList()).thenReturn(flow {
            emit(
                Resource.Success(
                    TestConstants.gamesModel
                )
            )
        })
        // When
        viewModel.getGamesList()
        // Then
        verify(useCase, atLeastOnce()).getGamesList()
        assertEquals(
            viewModel.getGamesList().value?.data?.count,
            useCase.getGamesList().asLiveData().value?.data?.count
        )
        assertEquals(
            viewModel.getGamesList().value?.data?.results?.size,
            useCase.getGamesList().asLiveData().value?.data?.results?.size
        )
        assertEquals(
            viewModel.getGamesList().value?.data?.results?.first()?.released,
            useCase.getGamesList().asLiveData().value?.data?.results?.first()?.released
        )
    }

    @Test
    fun test_GamesList_Return_Error() {
        // Given
        `when`(useCase.getGamesList()).thenReturn(flow {
            emit(Resource.Error(ERROR_MESSAGE))
        })
        // When
        viewModel.getGamesList()
        // Then
        verify(useCase, atLeastOnce()).getGamesList()
        assertEquals(
            viewModel.getGamesList().value?.message,
            useCase.getGamesList().asLiveData().value?.message
        )
    }

    @Test
    fun test_GamesList_Return_Loading() {
        // Given
        `when`(useCase.getGamesList()).thenReturn(flow {
            emit(Resource.Loading())
        })
        // When
        viewModel.getGamesList()
        // Then
        verify(useCase, atLeastOnce()).getGamesList()
        assertEquals(
            viewModel.getGamesList().value,
            useCase.getGamesList().asLiveData().value
        )
    }

    @Test
    fun test_GamesPaging_Return_Success() {
        // Given
        `when`(useCase.getGamesWithPaging(ONE)).thenReturn(flow {
            emit(
                Resource.Success(
                    TestConstants.gamesModel
                )
            )
        })
        // When
        viewModel.getGamesWithPaging(ONE)
        // Then
        verify(useCase, atLeastOnce()).getGamesWithPaging(ONE)
        assertEquals(
            viewModel.getGamesWithPaging(ONE).value?.data?.count,
            useCase.getGamesWithPaging(ONE).asLiveData().value?.data?.count
        )
        assertEquals(
            viewModel.getGamesWithPaging(ONE).value?.data?.results?.size,
            useCase.getGamesWithPaging(ONE).asLiveData().value?.data?.results?.size
        )
        assertEquals(
            viewModel.getGamesWithPaging(ONE).value?.data?.results?.first()?.released,
            useCase.getGamesWithPaging(ONE).asLiveData().value?.data?.results?.first()?.released
        )
    }

    @Test
    fun test_GamesPaging_Return_Error() {
        // Given
        `when`(useCase.getGamesWithPaging(ONE)).thenReturn(flow {
            emit(Resource.Error(ERROR_MESSAGE))
        })
        // When
        viewModel.getGamesWithPaging(ONE)
        // Then
        verify(useCase, atLeastOnce()).getGamesWithPaging(ONE)
        assertEquals(
            viewModel.getGamesWithPaging(ONE).value?.message,
            useCase.getGamesWithPaging(ONE).asLiveData().value?.message
        )
    }

    @Test
    fun test_GamesPaging_Return_Loading() {
        // Given
        `when`(useCase.getGamesWithPaging(ONE)).thenReturn(flow {
            emit(Resource.Loading())
        })
        // When
        viewModel.getGamesWithPaging(ONE)
        // Then
        verify(useCase, atLeastOnce()).getGamesWithPaging(ONE)
        assertEquals(
            viewModel.getGamesWithPaging(ONE).value,
            useCase.getGamesWithPaging(ONE).asLiveData().value
        )
    }

    @Test
    fun test_GamesSearch_Return_Success() {
        // Given
        `when`(useCase.getSearchGames(ONE, NAME)).thenReturn(flow {
            emit(
                Resource.Success(
                    TestConstants.gamesModel
                )
            )
        })
        // When
        viewModel.getSearchGames(ONE, NAME)
        // Then
        verify(useCase, atLeastOnce()).getSearchGames(ONE, NAME)
        assertEquals(
            viewModel.getSearchGames(ONE, NAME).value?.data?.count,
            useCase.getSearchGames(ONE, NAME).asLiveData().value?.data?.count
        )
        assertEquals(
            viewModel.getSearchGames(ONE, NAME).value?.data?.results?.size,
            useCase.getSearchGames(ONE, NAME).asLiveData().value?.data?.results?.size
        )
        assertEquals(
            viewModel.getSearchGames(ONE, NAME).value?.data?.results?.first()?.released,
            useCase.getSearchGames(ONE, NAME).asLiveData().value?.data?.results?.first()?.released
        )
    }

    @Test
    fun test_GamesSearch_Return_Error() {
        // Given
        `when`(useCase.getSearchGames(ONE, NAME)).thenReturn(flow {
            emit(Resource.Error(ERROR_MESSAGE))
        })
        // When
        viewModel.getSearchGames(ONE, NAME)
        // Then
        verify(useCase, atLeastOnce()).getSearchGames(ONE, NAME)
        assertEquals(
            viewModel.getSearchGames(ONE, NAME).value?.message,
            useCase.getSearchGames(ONE, NAME).asLiveData().value?.message
        )
    }

    @Test
    fun test_GamesSearch_Return_Loading() {
        // Given
        `when`(useCase.getSearchGames(ONE, NAME)).thenReturn(flow {
            emit(Resource.Loading())
        })
        // When
        viewModel.getSearchGames(ONE, NAME)
        // Then
        verify(useCase, atLeastOnce()).getSearchGames(ONE, NAME)
        assertEquals(
            viewModel.getSearchGames(ONE, NAME).value,
            useCase.getSearchGames(ONE, NAME).asLiveData().value
        )
    }
}