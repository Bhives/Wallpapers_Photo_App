package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.graphics.Color
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

        val favoritesViewPager = binding.favoritesViewPager
        favoritesViewPager.adapter = FavoritesFragmentPagerAdapter(childFragmentManager)
        favoritesViewPager.currentItem = 0
        favoritesViewPager.setPageTransformer(
            false
        ) { v, pos ->
            val opacity = abs(abs(pos) - 1)
            v.alpha = opacity
        }
        binding.apply {
            favoritesTabLayout.setupWithViewPager(favoritesViewPager, true)
            favoritesTabLayout.getTabAt(0)?.setIcon(R.drawable.ic_photo)
            favoritesTabLayout.getTabAt(1)?.setIcon(R.drawable.ic_search_query)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}