package com.mandiri.gamesapp.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mandiri.gamesapp.R
import com.mandiri.gamesapp.common.Constants.ZERO
import com.mandiri.gamesapp.databinding.ActivityDetailBinding
import com.mandiri.gamesapp.domain.Resource
import com.mandiri.gamesapp.domain.games.model.GamesDetailModel
import com.mandiri.gamesapp.extension.setOnClickWithThrottle
import com.mandiri.gamesapp.extension.showOrHide
import com.mandiri.gamesapp.extension.showToast
import com.mandiri.gamesapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    private val viewModel: DetailViewModel by viewModels()

    override fun getViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showBackButton()
        viewModel.gameId = intent.getIntExtra(EXTRA_ID_DATA, ZERO)
        getMoviesDetail(movieId = viewModel.gameId)
        getFavoriteStatus()
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListener() {
        binding.fabFavorite.setOnClickWithThrottle {
            if (viewModel.isFavorite) {
                deleteFromFavorite()
            } else addToFavorite()
        }
    }

    private fun getMoviesDetail(movieId: Int) {
        viewModel.getGamesDetail(movieId).observe(this) { res ->
            when (res) {
                is Resource.Success -> {
                    setLoadingVisibility(false)
                    if (res.data != null) {
                        viewModel.gameDetailModel = res.data
                        setView(res.data)
                    }
                }
                is Resource.Error -> {
                    setLoadingVisibility(false)
                    showToast(res.message.orEmpty())
                }
                is Resource.Loading -> {
                    setLoadingVisibility(true)
                }
            }
        }
    }

    private fun setLoadingVisibility(isShow: Boolean) {
        with(binding) {
            progressBar.showOrHide(isShow)
            fabFavorite.isEnabled = !isShow
        }
    }

    private fun setView(data: GamesDetailModel) {
        with(binding) {
            textViewTitle.text = data.name
            textViewDate.text = getString(R.string.released_text, data.released)
            textViewRating.text = getString(R.string.rating_text, data.rating.toString())
            textViewPlayedTime.text = getString(R.string.played_time_text, data.playtime.toString())
            textViewDesc.text = data.descriptionRaw
            with(imageView) {
                Glide.with(context)
                    .load(data.backgroundImage)
                    .into(this)
            }
        }
    }

    private fun getFavoriteStatus() {
        lifecycleScope.launch {
            viewModel.getGameByIdFromFavorite(viewModel.gameId).collect {
                viewModel.gameItemModel = it
                viewModel.isFavorite = it != null
                setFabButtonStatus()
                initListener()
            }
        }
    }

    private fun setFabButtonStatus() {
        val resId = if (viewModel.isFavorite) {
            R.drawable.ic_favorite_fill
        } else R.drawable.ic_favorite
        binding.fabFavorite.setImageResource(resId)
    }

    private fun addToFavorite() {
        viewModel.gameDetailModel?.let {
            viewModel.addGameToFavorite(it)
            viewModel.isFavorite = true
            setFabButtonStatus()
        } ?: showToast(getString(R.string.message_error_add_favorite))
    }

    private fun deleteFromFavorite() {
        viewModel.gameItemModel?.let {
            viewModel.deleteGameFromFavorite(
                it
            )
            viewModel.isFavorite = false
            setFabButtonStatus()
        } ?: showToast(getString(R.string.message_error_delete_favorite))
    }

    companion object {
        const val EXTRA_ID_DATA = "EXTRA_ID_DATA"
        fun startActivity(context: Context, gameId: Int) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_ID_DATA, gameId)
            })
        }
    }
}