package com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites

import androidx.lifecycle.viewModelScope
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteSearchQueriesViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    BaseViewModel() {

    fun getFavoriteSearchQueries() = photosRepository.getFavoriteSearchQueries()

    fun removeFromFavorites(searchQuery: SearchQuery) {
        searchQuery.queryFavoriteFlag = false
        viewModelScope.launch(Dispatchers.IO) {
            photosRepository.removeSearchQueryFromFavorites(searchQuery)
        }
    }
}