package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentPhotoSearchBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.PhotosSearchAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.PhotosSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

lateinit var _binding: FragmentPhotoSearchBinding
val binding get() = _binding!!

@AndroidEntryPoint
class PhotosSearchFragment : BaseFragment() {

    private val photosSearchViewModel by viewModels<PhotosSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentPhotoSearchBinding.inflate(inflater, container, false)
        setAdapter()
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
        val photosSearchAdapter = PhotosSearchAdapter()
        binding.apply {
            photosRecyclerView.setHasFixedSize(true)
            photosRecyclerView.adapter = photosSearchAdapter
        }
        photosSearchViewModel.photosAll.observe(viewLifecycleOwner) {
            photosSearchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null!!
    }
}