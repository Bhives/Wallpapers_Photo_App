package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.View
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentFavoritesBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.FavoritesFragmentPagerAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import kotlin.math.abs

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)

        val articlesViewPager = binding.favoritesViewPager
        articlesViewPager.adapter = FavoritesFragmentPagerAdapter(childFragmentManager)
        articlesViewPager.currentItem = 1
        articlesViewPager.setPageTransformer(false
        ) { v, pos ->
            val opacity = abs(abs(pos) - 1)
            v.alpha = opacity
        }

        binding.favoritesTabLayout.setupWithViewPager(articlesViewPager, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}