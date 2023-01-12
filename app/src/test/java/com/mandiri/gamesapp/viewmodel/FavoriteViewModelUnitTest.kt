package com.mandiri.gamesapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mandiri.gamesapp.domain.favorite.usecase.IFavoriteUseCase
import com.mandiri.gamesapp.ui.favorite.FavoriteViewModel
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

class FavoriteViewModelUnitTest {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteUseCase: IFavoriteUseCase

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        favoriteUseCase = Mockito.mock(IFavoriteUseCase::class.java)
        viewModel = FavoriteViewModel(favoriteUseCase)
    }
}