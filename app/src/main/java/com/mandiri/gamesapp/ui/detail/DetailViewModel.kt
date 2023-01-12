package com.mandiri.gamesapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mandiri.gamesapp.common.Constants.ZERO
import com.mandiri.gamesapp.domain.favorite.usecase.IFavoriteUseCase
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import com.mandiri.gamesapp.domain.games.usecase.IGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val gamesUseCase: IGamesUseCase,
    private val favoriteUseCase: IFavoriteUseCase
) : ViewModel() {
    var gameId: Int = ZERO
    var gameDetailModel: GamesDetailModel? = null
    var gameItemModel: GameItemModel? = null
    var isFavorite: Boolean = false
    fun getGamesDetail(gameId: Int) = gamesUseCase.getGamesDetail(gameId).asLiveData()
    fun addGameToFavorite(item: GamesDetailModel) = favoriteUseCase.addGameToFavorite(item)
    fun deleteGameFromFavorite(item: GameItemModel) = favoriteUseCase.deleteGameFromFavorite(item)
    fun getGameByIdFromFavorite(gameId: Int) = favoriteUseCase.getGameById(gameId)
}