package com.mandiri.gamesapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mandiri.gamesapp.databinding.FragmentFavoriteBinding
import com.mandiri.gamesapp.ui.base.BaseFragment
import com.mandiri.gamesapp.ui.favorite.adapter.FavoriteAdapter
import com.mandiri.gamesapp.ui.favorite.adapter.FavoriteLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    override fun onInitViewModel() {
        viewModel =
            ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    override fun onMain(savedInstanceState: Bundle?) {
        initRecyclerView()
        getFavoriteList()
    }

    override fun onResume() {
        super.onResume()
        favoriteAdapter.refresh()
    }

    private fun initRecyclerView() {
        favoriteAdapter = FavoriteAdapter()
        binding.layoutRv.rvGames.adapter = favoriteAdapter.withLoadStateFooter(
            FavoriteLoadStateAdapter()
        )
    }

    private fun getFavoriteList() {
        lifecycleScope.launch {
            viewModel.data.collectLatest {
                favoriteAdapter.submitData(it)
            }
        }
    }

    override fun onReleaseReference() {
        binding.layoutRv.rvGames.adapter = null
    }
}