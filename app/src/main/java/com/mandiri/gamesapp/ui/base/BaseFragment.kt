package com.mandiri.gamesapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding> : Fragment() {
    private var _binding: VBinding? = null
    protected val binding get() = _binding!!

    protected abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): VBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onInitViewModel()
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onMain(savedInstanceState)
    }

    protected abstract fun onInitViewModel()

    protected abstract fun onMain(savedInstanceState: Bundle?)

    protected abstract fun onReleaseReference()

    override fun onDestroyView() {
        super.onDestroyView()
        onReleaseReference()
        _binding = null
    }
}