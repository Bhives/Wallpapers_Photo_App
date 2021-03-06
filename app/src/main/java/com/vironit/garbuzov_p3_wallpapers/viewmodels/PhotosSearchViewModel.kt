package com.vironit.garbuzov_p3_wallpapers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosSearchViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    ViewModel() {

    private val currentQuery = MutableLiveData(CURRENT_QUERY)

    val photosAll = currentQuery.switchMap { queryString ->
        photosRepository.getPhotosSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        CURRENT_QUERY=query
        currentQuery.value = query
    }

    fun insertSearchQuery(searchQuery: SearchQuery) {
        viewModelScope.launch(Dispatchers.Default) {
            photosRepository.insertSearchQuery(searchQuery)
        }
    }

    fun getAllSearchQueriesValues() = photosRepository.getAllSearchQueriesValues()

    companion object {
        private var CURRENT_QUERY = "mandalorian"
    }
}