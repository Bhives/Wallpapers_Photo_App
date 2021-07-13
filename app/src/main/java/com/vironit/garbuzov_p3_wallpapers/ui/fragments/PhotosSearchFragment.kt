package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentPhotoSearchBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.SearchPhotosAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.PhotosSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PhotosSearchFragment : BaseFragment(R.layout.fragment_photo_search), OnItemClickListener {

    private var _binding: FragmentPhotoSearchBinding? = null
    val binding get() = _binding!!
    private val photosSearchViewModel by viewModels<PhotosSearchViewModel>()
    private val photosSearchAdapter = SearchPhotosAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPhotoSearchBinding.bind(view)
        setAdapter()
        searchPhotos()
    }

    private fun searchPhotos() {
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
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
                    //switchNoSearchResultsState(false)
                    binding.photosRecyclerView.scrollToPosition(0)
                    photosSearchViewModel.searchPhotos(query)
                }
                //if (photosSearchAdapter.itemCount < 1) {
                //    switchNoSearchResultsState(true)
                //}
                return true
            }
        })
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

    private fun switchNoSearchResultsState(stateFlag: Boolean) {
        binding.apply {
            if (stateFlag) {
                photosRecyclerView.isVisible = false
                errorText.text = context?.resources?.getString(R.string.no_search_results_alert)
                errorText.isVisible = true
            } else {
                errorText.isVisible = false
                photosRecyclerView.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: Photo) {
        val action =
            PhotosSearchFragmentDirections.actionPhotosSearchFragmentToCurrentPhotoFragment(photo)
        findNavController().navigate(action)
    }

    companion object {
        var photosList: PagingData<Photo>? = null
    }
}