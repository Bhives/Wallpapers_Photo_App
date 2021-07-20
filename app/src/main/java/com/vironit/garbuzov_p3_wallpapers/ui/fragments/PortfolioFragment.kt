package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentPortfolioBinding
import com.vironit.garbuzov_p3_wallpapers.ui.bindingActivity
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment

class PortfolioFragment : BaseFragment(R.layout.fragment_portfolio) {

    private val args by navArgs<PortfolioFragmentArgs>()
    private var _binding: FragmentPortfolioBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPortfolioBinding.bind(view)
        bindingActivity.fragmentsMenu.isVisible = false
        binding.portfolioWebView.webViewClient = WebViewClient()
        binding.portfolioWebView.loadUrl(args.url)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}