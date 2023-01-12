package com.mandiri.gamesapp.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.mandiri.gamesapp.databinding.ItemFooterBinding
import com.mandiri.gamesapp.ui.base.LoadingViewHolder

class FavoriteLoadStateAdapter : LoadStateAdapter<LoadingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingViewHolder {
        return LoadingViewHolder(
            ItemFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) {
    }
}