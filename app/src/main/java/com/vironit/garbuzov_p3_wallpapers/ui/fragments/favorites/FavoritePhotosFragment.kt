package com.vironit.garbuzov_p3_wallpapers.ui.fragments.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val favoritePhotosAdapter = FavoritePhotosAdapter(this, listOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritePhotosBinding.bind(view)
        setAdapter(requireContext())
    }

    private fun setAdapter(
        context: Context
    ) {
        binding.apply {
            favoritePhotosRecyclerView.setHasFixedSize(true)
            favoritePhotosRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            favoritePhotosRecyclerView.adapter = favoritePhotosAdapter
        }
        photosFavoritesViewModel.getFavoritePhotos().observe(viewLifecycleOwner, {
            favoritePhotosAdapter.photosList = it
            photosList = it
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

    companion object {
        var photosList: List<Photo> = listOf()
    }
}