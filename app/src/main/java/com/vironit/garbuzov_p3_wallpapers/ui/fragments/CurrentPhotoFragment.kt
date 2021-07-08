package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment

class CurrentPhotoFragment : BaseFragment() {

    private val args by navArgs<CurrentPhotoFragmentArgs>()
    private var _binding: FragmentCurrentPhotoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentCurrentPhotoBinding.inflate(inflater, container, false)
        attachPhoto()
        return binding.root
    }

    private fun attachPhoto() {
        binding.apply {
            val photo = args.photo
            Glide.with(this@CurrentPhotoFragment)
                .load(photo.urls.full)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(selectedPhotoImageView)
            val author = photo.user.username
            authorTextView.text = "Photo by $author"
            photoDescriptionTextView.text = photo.description
        }
    }
}