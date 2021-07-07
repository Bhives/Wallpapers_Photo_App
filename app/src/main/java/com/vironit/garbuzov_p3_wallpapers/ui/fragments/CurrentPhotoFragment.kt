package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment

class CurrentPhotoFragment : BaseFragment() {

    private val args by navArgs<CurrentPhotoFragmentArgs>()
    lateinit var _binding: FragmentCurrentPhotoBinding
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentCurrentPhotoBinding.inflate(inflater, container, false)

        binding.apply {
            val photo = args
            Glide.with(this@CurrentPhotoFragment)
                .load(photo.urls)
        }

        return binding.root
    }
}