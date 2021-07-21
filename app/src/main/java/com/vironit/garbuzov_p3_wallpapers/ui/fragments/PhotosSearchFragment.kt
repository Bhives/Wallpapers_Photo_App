package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentPhotoSearchBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnPhotosItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.SearchPhotosAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.PhotosSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photo_search.*

@AndroidEntryPoint
class PhotosSearchFragment : BaseFragment(R.layout.fragment_photo_search),
    OnPhotosItemClickListener {

    private val args by navArgs<PhotosSearchFragmentArgs>()
    private var _binding: FragmentPhotoSearchBinding? = null
    val binding get() = _binding!!
    private val photosSearchViewModel by viewModels<PhotosSearchViewModel>()
    private val photosSearchAdapter = SearchPhotosAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPhotoSearchBinding.bind(view)
        setAdapter()
        searchPhotos()
        if (findNavController().navigateUp()) {
            searchPhotoWithArgs()
        }
    }

    private fun setAdapter() {
        binding.apply {
            photosRecyclerView.setHasFixedSize(true)
            photosRecyclerView.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            photosRecyclerView.adapter = photosSearchAdapter
        }
        photosSearchViewModel.photosAll.observe(viewLifecycleOwner) {
            photosSearchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            photosList = it
        }
    }

    private fun searchPhotos() {
        binding.photoSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.photoSearchView.clearFocus()
                photosSearchViewModel.insertSearchQuery(
                    SearchQuery(
                        query.toString(),
                        photosSearchAdapter.itemCount,
                        "",
                        false
                    )
                )
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    photosRecyclerView.scrollToPosition(0)
                    photosSearchViewModel.searchPhotos(query)
                }
                return true
            }
        })
    }

    private fun searchPhotoWithArgs() {
        binding.photosRecyclerView.scrollToPosition(0)
        photosSearchViewModel.searchPhotos(args.searchQuery.queryText)
    }

    override fun onItemClick(photo: Photo) {
        val action =
            PhotosSearchFragmentDirections.actionPhotosSearchFragmentToCurrentPhotoFragment(photo)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var photosList: PagingData<Photo>? = null
    }
}