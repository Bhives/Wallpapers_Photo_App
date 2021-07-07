package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentPhotoSearchBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.PhotosSearchAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.PhotosSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosSearchFragment : BaseFragment() {

    lateinit var _binding: FragmentPhotoSearchBinding
    val binding get() = _binding!!
    private val photosSearchViewModel by viewModels<PhotosSearchViewModel>()
    private val photosSearchAdapter = PhotosSearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoSearchBinding.inflate(inflater, container, false)
        setAdapter()
        binding.photoSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    switchNoSearchResultsState(false)
                    binding.photosRecyclerView.scrollToPosition(0)
                    photosSearchViewModel.searchPhotos(query)
                    binding.photoSearchView.clearFocus()
                }
                if (photosSearchAdapter.itemCount<1){
                    switchNoSearchResultsState(true)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return binding.root
    }

    //private fun setViewModel(){
    //    val photosRepository = PhotosRepository(PhotosDatabase(requireContext()))
    //    val employeesViewModelFactory = PhotosViewModelFactory(photosRepository)
    //    photosSearchViewModel =
    //        ViewModelProvider(
    //            ViewModelStore(),
    //            employeesViewModelFactory
    //        ).get(PhotosSearchViewModel::class.java)
    //    setAdapter(photosSearchViewModel)
    //}

    private fun setAdapter() {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_NETWORK_STATE
            ),
            100
        )
        //val photosSearchAdapter = PhotosSearchAdapter()
        binding.apply {
            photosRecyclerView.setHasFixedSize(true)
            photosRecyclerView.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            photosRecyclerView.adapter = photosSearchAdapter
        }
        photosSearchViewModel.photosAll.observe(viewLifecycleOwner) {
            photosSearchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        if (photosSearchAdapter.itemCount<1){
            switchNoSearchResultsState(true)
        }
    }

    private fun switchNoSearchResultsState(stateFlag: Boolean){
        binding.apply {
            if(!stateFlag) {
                photosRecyclerView.isVisible = false
                errorText.text = context?.resources?.getString(R.string.no_search_results_alert)
                errorText.isVisible = true
            }
            else{
                errorText.isVisible = false
                photosRecyclerView.isVisible = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null!!
    }
}