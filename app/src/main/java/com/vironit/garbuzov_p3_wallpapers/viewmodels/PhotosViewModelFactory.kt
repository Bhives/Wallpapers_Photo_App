package com.vironit.garbuzov_p3_wallpapers.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository

class PhotosViewModelFactory(private val photosRepository: PhotosRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotosSearchViewModel(photosRepository) as T
    }
}