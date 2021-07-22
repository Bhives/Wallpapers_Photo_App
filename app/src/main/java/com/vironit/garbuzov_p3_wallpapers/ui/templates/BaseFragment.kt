package com.vironit.garbuzov_p3_wallpapers.ui.templates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

abstract class BaseFragment(private val contentLayoutId: Int) : Fragment(contentLayoutId) {

    open var binding: ViewDataBinding? = null
    open val viewModel by viewModels<ViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root ?: inflater.inflate(contentLayoutId, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}