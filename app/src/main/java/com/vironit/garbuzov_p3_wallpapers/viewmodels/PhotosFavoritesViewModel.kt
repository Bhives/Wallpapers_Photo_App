package com.vironit.garbuzov_p3_wallpapers.viewmodels

import com.vironit.garbuzov_p3_wallpapers.data.Photo
import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseViewModel

class PhotosFavoritesViewModel (private val photosRepository: PhotosRepository): BaseViewModel() {

    fun insertToFavorites(photo: Photo) {
        photosRepository.insertToFavorites(photo)
    }

    fun getFavoritePhotos() = photosRepository.getFavoritePhotos()

    fun removeFromFavorites(photo: Photo){
        photosRepository.removeFromFavorites(photo)
    }
}