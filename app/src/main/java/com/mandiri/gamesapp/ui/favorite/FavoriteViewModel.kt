package com.mandiri.gamesapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mandiri.gamesapp.common.ApiConstants
import com.mandiri.gamesapp.domain.favorite.usecase.IFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: IFavoriteUseCase
) : ViewModel() {
    val data = Pager(
        PagingConfig(
            pageSize = ApiConstants.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = ApiConstants.DEFAULT_PAGE_SIZE
        ),
    ) {
        favoriteUseCase.gamesPagingSource()
    }.flow.cachedIn(viewModelScope)
}