package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.fragments.favorites.FavoritePhotosFragment
import com.vironit.garbuzov_p3_wallpapers.ui.fragments.favorites.FavoriteSearchQueriesFragment

class FavoritesFragmentPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(
        fragmentManager,
        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavoritePhotosFragment()
            }
            1 -> {
                FavoriteSearchQueriesFragment()
            }
            else -> {
                FavoritePhotosFragment()
            }
        }
    }
}