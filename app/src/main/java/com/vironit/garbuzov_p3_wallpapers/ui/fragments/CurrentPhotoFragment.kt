package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.PhotosFavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentPhotoFragment : BaseFragment() {

    private val args by navArgs<CurrentPhotoFragmentArgs>()
    private val photosFavoritesViewModel by viewModels<PhotosFavoritesViewModel>()
    private var _binding: FragmentCurrentPhotoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentCurrentPhotoBinding.inflate(inflater, container, false)
        attachPhoto()
        photosFavoritesViewModel.setWallpaper(binding, requireContext(), this.requireActivity())
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_currentPhotoFragment_to_imageSearchFragment)
        }
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
            photoDescriptionTextView.text = photo.description

            val uri = Uri.parse(photo.user.portfolioUrl)
            val portfolioIntent = Intent(Intent.ACTION_VIEW, uri)

            authorTextView.apply {
                text = "Photo by ${photo.user.username}"
                setOnClickListener {
                    context.startActivity(portfolioIntent)
                }
                paint.isUnderlineText = true
            }
        }
    }
}