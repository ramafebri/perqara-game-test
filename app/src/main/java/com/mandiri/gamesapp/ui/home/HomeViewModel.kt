package com.mandiri.gamesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mandiri.gamesapp.common.ApiConstants
import com.mandiri.gamesapp.common.Constants.EMPTY
import com.mandiri.gamesapp.domain.games.usecase.IGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val gamesUseCase: IGamesUseCase) : ViewModel() {
    var currentPage: Int = ApiConstants.DEFAULT_PAGE
    var currentSearch: String = EMPTY
    fun getGamesList() = gamesUseCase.getGamesList().asLiveData()
    fun getGamesWithPaging(page: Int) = gamesUseCase.getGamesWithPaging(page).asLiveData()
    fun getSearchGames(page: Int, search: String) = gamesUseCase.getSearchGames(page, search).asLiveData()
}