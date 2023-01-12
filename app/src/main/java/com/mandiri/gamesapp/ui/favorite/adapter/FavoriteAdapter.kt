package com.mandiri.gamesapp.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mandiri.gamesapp.databinding.ItemGameBinding
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.ui.base.GamesViewHolder

class FavoriteAdapter :
    PagingDataAdapter<GameItemModel, GamesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        return GamesViewHolder(
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GameItemModel>() {
            override fun areItemsTheSame(oldItem: GameItemModel, newItem: GameItemModel): Boolean =
                oldItem.gameId == newItem.gameId

            override fun areContentsTheSame(
                oldItem: GameItemModel,
                newItem: GameItemModel
            ): Boolean =
                oldItem == newItem
        }
    }
}