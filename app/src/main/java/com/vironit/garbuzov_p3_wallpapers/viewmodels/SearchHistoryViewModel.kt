package com.vironit.garbuzov_p3_wallpapers.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    ViewModel() {

    fun getAllSearchQueries(): LiveData<List<SearchQuery>> {
        var searchQueriesList = MutableLiveData(listOf<SearchQuery>()) as LiveData<List<SearchQuery>>
        viewModelScope.launch(Dispatchers.Default)  {
            searchQueriesList = photosRepository.getAllSearchQueries()
        }
        return searchQueriesList
    }

    fun addSearchQueryToFavorites(searchQuery: SearchQuery) {
        searchQuery.queryFavoriteFlag=true
        viewModelScope.launch(Dispatchers.Default) {
            photosRepository.addSearchQueryToFavorites(searchQuery)
        }
    }

    fun removeFromFavorites(searchQuery: SearchQuery) {
        searchQuery.queryFavoriteFlag = false
        viewModelScope.launch(Dispatchers.Default) {
            photosRepository.removeSearchQueryFromFavorites(searchQuery)
        }
    }
}