package com.vironit.garbuzov_p3_wallpapers.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentFavoritePhotosBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites.FavoritePhotosAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnPhotosItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoritePhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePhotosFragment : BaseFragment(R.layout.fragment_favorite_photos),
    OnPhotosItemClickListener {

    private var _binding: FragmentFavoritePhotosBinding? = null
    val binding get() = _binding!!
    private val photosFavoritesViewModel by viewModels<FavoritePhotosViewModel>()
    lateinit var favoritePhotosAdapter: FavoritePhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritePhotosBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        favoritePhotosAdapter = FavoritePhotosAdapter(this, listOf())
        binding.apply {
            favoritePhotosRecyclerView.adapter = favoritePhotosAdapter
        }
        photosFavoritesViewModel.getFavoritePhotos().observe(viewLifecycleOwner, {
            favoritePhotosAdapter.photosList = it
        })
    }

    override fun onItemClick(photo: Photo) {
        val action =
            com.vironit.garbuzov_p3_wallpapers.ui.fragments.FavoritesFragmentDirections.actionFavoritesFragmentToCurrentPhotoFragment(
                photo
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}