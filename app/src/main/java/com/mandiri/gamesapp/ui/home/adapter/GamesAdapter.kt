package com.mandiri.gamesapp.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mandiri.gamesapp.common.Constants
import com.mandiri.gamesapp.databinding.ItemFooterBinding
import com.mandiri.gamesapp.databinding.ItemGameBinding
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.ui.base.GamesViewHolder
import com.mandiri.gamesapp.ui.base.LoadingViewHolder

class GamesAdapter(private var listener: AdapterListener?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<GameItemModel>() {
        override fun areItemsTheSame(
            oldItem: GameItemModel,
            newItem: GameItemModel
        ): Boolean {
            return oldItem.gameId == newItem.gameId
        }

        override fun areContentsTheSame(
            oldItem: GameItemModel,
            newItem: GameItemModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val listDiffer = AsyncListDiffer(this, diffCallback)
    private var gamesList = mutableListOf<GameItemModel>()
    private var isLastPage = false

    fun setDifferData(data: List<GameItemModel>, totalGames: Int) {
        if (data.isEmpty()) return
        gamesList.addAll(data)
        setIsTheLastPage(gamesList.size == totalGames)
        listDiffer.submitList(gamesList)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetList() {
        gamesList.clear()
        setIsTheLastPage(false)
        listDiffer.submitList(null)
        notifyDataSetChanged()
    }

    private fun setIsTheLastPage(value: Boolean) {
        isLastPage = value
    }

    fun releaseReference() {
        listener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.ADAPTER_FOOTER) {
            LoadingViewHolder(
                ItemFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            GamesViewHolder(
                ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoadingViewHolder) {
            if (!isLastPage) {
                listener?.loadNextPage()
            }
            return
        }
        if (holder is GamesViewHolder) {
            holder.bind(listDiffer.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return if (isLastPage || listDiffer.currentList.isEmpty()) {
            listDiffer.currentList.size
        } else listDiffer.currentList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listDiffer.currentList.size) {
            Constants.ADAPTER_FOOTER
        } else Constants.LIST_ITEM
    }

    interface AdapterListener {
        fun loadNextPage()
    }
}