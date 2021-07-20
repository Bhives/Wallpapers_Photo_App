package com.vironit.garbuzov_p3_wallpapers.viewmodels

import androidx.lifecycle.viewModelScope
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchHistoryViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    BaseViewModel() {

    fun getAllSearchQueries() = photosRepository.getAllSearchQueries()

    fun getSearchQuery(searchQueryText: String) = photosRepository.getSearchQuery(searchQueryText)

    fun getFavoriteSearchQueries() = photosRepository.getFavoriteSearchQueries()

    fun searchQueryIsInFavorites(searchQuery: SearchQuery): Boolean {
        return getFavoriteSearchQueries().value?.contains(searchQuery)==true
    }

    fun addSearchQueryToFavorites(searchQuery: SearchQuery) {
        searchQuery.queryFavoriteFlag=true
        viewModelScope.launch(Dispatchers.IO) {
            photosRepository.addSearchQueryToFavorites(searchQuery)
        }
    }

    fun removeFromFavorites(searchQuery: SearchQuery) {
        searchQuery.queryFavoriteFlag = false
        viewModelScope.launch(Dispatchers.IO) {
            photosRepository.removeSearchQueryFromFavorites(searchQuery)
        }
    }
}