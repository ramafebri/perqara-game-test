package com.mandiri.gamesapp.domain.favorite.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mandiri.gamesapp.common.Constants.ONE
import com.mandiri.gamesapp.data.favorite.local.GamesDao
import com.mandiri.gamesapp.domain.games.model.GameItemModel

class GamesPagingSource(
    private val dao: GamesDao
) :
    PagingSource<Int, GameItemModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameItemModel> {
        val page = params.key ?: STARTING_KEY
        return try {
            val entities = dao.getGamesList(params.loadSize, page * params.loadSize)
            LoadResult.Page(
                data = GameItemModel.fromListEntity(entities),
                prevKey = if (page == STARTING_KEY) null else page - ONE,
                nextKey = if (entities.isEmpty()) null else page + ONE
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GameItemModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(ONE) ?: anchorPage?.nextKey?.minus(ONE)
        }
    }

    companion object {
        const val STARTING_KEY = 0
    }
}