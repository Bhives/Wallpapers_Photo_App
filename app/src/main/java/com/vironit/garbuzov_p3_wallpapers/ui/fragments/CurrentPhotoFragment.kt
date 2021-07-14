package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoritePhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentPhotoFragment : BaseFragment(R.layout.fragment_current_photo),
    CompoundButton.OnCheckedChangeListener {

    private val args by navArgs<CurrentPhotoFragmentArgs>()
    private val photosFavoritesViewModel by viewModels<FavoritePhotosViewModel>()
    private var _binding: FragmentCurrentPhotoBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrentPhotoBinding.bind(view)
        attachPhoto()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.setWallpaperButton.setOnClickListener {
            photosFavoritesViewModel.setWallpaper(binding, requireContext(), this.requireActivity())
        }
        binding.favoritesToggle.setOnCheckedChangeListener(this)
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

            if (photosFavoritesViewModel.photoIsInFavorites(photo.id)) {
                favoritesToggle.isChecked
            } else {
                !favoritesToggle.isChecked
            }
            //val uri = Uri.parse(photo.user.portfolioUrl)
            //val portfolioIntent = Intent(Intent.ACTION_VIEW, uri)
            authorTextView.apply {
                text = "Photo by ${photo.user.username}"
                setOnClickListener {
                    //context.startActivity(portfolioIntent)
                }
                paint.isUnderlineText = true
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            photosFavoritesViewModel.insertToFavorites(args.photo)
        } else {
            photosFavoritesViewModel.removeFromFavorites(args.photo)
        }
    }
}