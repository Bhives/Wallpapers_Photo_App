package com.vironit.garbuzov_p3_wallpapers.viewmodels

import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    BaseViewModel() {

    fun getAllSearchQueries() = photosRepository.getAllSearchQueries()
}