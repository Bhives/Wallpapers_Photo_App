package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentPhotoSearchBinding
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.PhotosSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

lateinit var _binding: FragmentPhotoSearchBinding
val binding get() = _binding!!

@AndroidEntryPoint
class PhotosSearchFragment : BaseFragment() {

    private val photosSearchViewModel: PhotosSearchViewModel by viewModels<PhotosSearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentPhotoSearchBinding.inflate(inflater, container, false)

        photosSearchViewModel.photosAll.observe(viewLifecycleOwner) {

        }
        return binding.root
    }

    fun bindPhoto(photo: Photo){
        binding.apply {
            Glide.with(requireView())
                .load(photo.urls.regular)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_launcher_foreground)
                .into(currentPhoto)

            photoAuthor.text=photo.user.username
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.photoId == newItem.photoId
            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null!!
    }
}