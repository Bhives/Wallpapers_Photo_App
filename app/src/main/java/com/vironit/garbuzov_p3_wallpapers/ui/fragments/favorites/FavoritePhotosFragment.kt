package com.vironit.garbuzov_p3_wallpapers.ui.fragments.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentFavoritePhotosBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnPhotosItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites.FavoritePhotosAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoritePhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_photos.*

@AndroidEntryPoint
class FavoritePhotosFragment : BaseFragment<FragmentFavoritePhotosBinding>(R.layout.fragment_favorite_photos),
    OnPhotosItemClickListener {

    override val viewModel by viewModels<FavoritePhotosViewModel>()
    lateinit var favoritePhotosAdapter: FavoritePhotosAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        favoritePhotosAdapter = FavoritePhotosAdapter(this, listOf())
        favoritePhotosRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewModel.getFavoritePhotos().observe(viewLifecycleOwner, {
            favoritePhotosAdapter.photosList = it
        })
        favoritePhotosRecyclerView.adapter = favoritePhotosAdapter
    }

    override fun onItemClick(photo: Photo) {
        val action =
            com.vironit.garbuzov_p3_wallpapers.ui.fragments.FavoritesFragmentDirections.actionFavoritesFragmentToCurrentPhotoFragment(
                photo
            )
        findNavController().navigate(action)
    }
}