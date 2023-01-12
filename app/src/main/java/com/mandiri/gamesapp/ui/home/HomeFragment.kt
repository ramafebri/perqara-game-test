package com.mandiri.gamesapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.mandiri.gamesapp.common.Constants.ONE
import com.mandiri.gamesapp.databinding.FragmentHomeBinding
import com.mandiri.gamesapp.domain.Resource
import com.mandiri.gamesapp.extension.showOrHide
import com.mandiri.gamesapp.extension.showToast
import com.mandiri.gamesapp.ui.base.BaseFragment
import com.mandiri.gamesapp.ui.home.adapter.GamesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), GamesAdapter.AdapterListener {
    private lateinit var viewModel: HomeViewModel
    private lateinit var gamesAdapter: GamesAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onInitViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onMain(savedInstanceState: Bundle?) {
        initRecyclerView()
        initListener()
        getGamesList()
    }

    private fun initRecyclerView() {
        gamesAdapter = GamesAdapter(this)
        binding.layoutRv.rvGames.adapter = gamesAdapter
    }

    private fun initListener() {
        binding.textInputEditTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                getSearchGames(
                    ONE,
                    s.toString(),
                    true
                )
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    private fun getGamesList(isReset: Boolean = false) {
        if (viewModel.currentSearch.isNotEmpty()) return
        viewModel.currentPage = ONE
        viewModel.getGamesList().observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Success -> {
                    setLoadingVisibility(false)
                    if (isReset) gamesAdapter.resetList()
                    if (res.data != null) {
                        gamesAdapter.setDifferData(res.data.results, res.data.count)
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

    private fun getGamesWithPaging(page: Int) {
        viewModel.currentPage = page
        viewModel.getGamesWithPaging(page).observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Success -> {
                    if (res.data != null) {
                        gamesAdapter.setDifferData(res.data.results, res.data.count)
                    }
                }
                is Resource.Error -> {
                    showToast(res.message.orEmpty())
                }
                is Resource.Loading -> {
                    // Do nothing
                }
            }
        }
    }

    private fun getSearchGames(page: Int, searchValue: String, isFirstTime: Boolean) {
        viewModel.currentSearch = searchValue
        if (searchValue.isEmpty()) {
            getGamesList(true)
            return
        }
        viewModel.currentPage = page
        viewModel.getSearchGames(page, searchValue).observe(viewLifecycleOwner) { res ->
            when (res) {
                is Resource.Success -> {
                    setLoadingVisibility(false)
                    if (isFirstTime) gamesAdapter.resetList()
                    if (res.data != null) {
                        gamesAdapter.setDifferData(res.data.results, res.data.count)
                    }
                }
                is Resource.Error -> {
                    setLoadingVisibility(false)
                    showToast(res.message.orEmpty())
                }
                is Resource.Loading -> {
                    if (isFirstTime) setLoadingVisibility(true)
                }
            }
        }
    }

    private fun setLoadingVisibility(isShow: Boolean) {
        with(binding) {
            progressBar.showOrHide(isShow)
            layoutRv.rvGames.showOrHide(!isShow)
        }
    }

    override fun loadNextPage() {
        if (viewModel.currentSearch.isEmpty()) {
            getGamesWithPaging(viewModel.currentPage + ONE)
        } else {
            getSearchGames(viewModel.currentPage + ONE, viewModel.currentSearch, false)
        }
    }

    override fun onReleaseReference() {
        binding.layoutRv.rvGames.adapter = null
        gamesAdapter.releaseReference()
    }
}