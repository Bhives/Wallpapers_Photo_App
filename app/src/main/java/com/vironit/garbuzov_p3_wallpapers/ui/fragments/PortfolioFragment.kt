package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.ui.bindingActivity
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import kotlinx.android.synthetic.main.fragment_portfolio.*

class PortfolioFragment : BaseFragment(R.layout.fragment_portfolio) {

    private val args by navArgs<PortfolioFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindingActivity.fragmentsMenu.isVisible = false
        portfolioWebView.webViewClient = WebViewClient()
        portfolioWebView.loadUrl(args.url)
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}