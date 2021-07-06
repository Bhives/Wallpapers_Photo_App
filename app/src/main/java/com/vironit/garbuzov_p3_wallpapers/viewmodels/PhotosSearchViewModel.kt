package com.vironit.garbuzov_p3_wallpapers.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosSearchViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    BaseViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photosAll = currentQuery.switchMap { queryString->
        photosRepository.getPhotosSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String){
        currentQuery.value = query
    }

    companion object{
        private const val DEFAULT_QUERY = "mandalorian"
    }

    //fun insertToFavorites(photo: Photo) {
    //    photosRepository.insertToFavorites(photo)
    //}

    //fun getFavoritePhotos() = photosRepository.getFavoritePhotos()
}