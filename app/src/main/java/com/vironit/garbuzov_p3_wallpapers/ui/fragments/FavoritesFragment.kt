package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.View
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentFavoritesBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.FavoritesFragmentPagerAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlin.math.abs

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoritesViewPager = favoritesViewPager
        favoritesViewPager.adapter = FavoritesFragmentPagerAdapter(childFragmentManager)
        favoritesViewPager.currentItem = 0
        favoritesViewPager.setPageTransformer(
            false
        ) { v, pos ->
            val opacity = abs(abs(pos) - 1)
            v.alpha = opacity
        }
        favoritesTabLayout.setupWithViewPager(favoritesViewPager, true)
        favoritesTabLayout.getTabAt(0)?.setIcon(R.drawable.ic_photo)
        favoritesTabLayout.getTabAt(1)?.setIcon(R.drawable.ic_search_query)
    }
}