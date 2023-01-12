package com.mandiri.gamesapp.ui.base

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mandiri.gamesapp.R
import com.mandiri.gamesapp.databinding.ItemGameBinding
import com.mandiri.gamesapp.domain.games.model.GameItemModel
import com.mandiri.gamesapp.ui.detail.DetailActivity

class GamesViewHolder(private val binding: ItemGameBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GameItemModel) {
        with(binding) {
            textViewTitle.text = item.name
            textViewDate.text =
                root.context.getString(R.string.released_text, item.released)
            textViewRating.text =
                root.context.getString(R.string.rating_text, item.rating.toString())
            with(imageView) {
                Glide.with(context)
                    .load(item.backgroundImage)
                    .into(this)
            }
            root.setOnClickListener {
                DetailActivity.startActivity(root.context, item.gameId)
            }
        }
    }
}