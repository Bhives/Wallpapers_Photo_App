package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment

class FavoritePhotosFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_photos, container, false)
    }
}