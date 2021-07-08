package com.vironit.garbuzov_p3_wallpapers.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vironit.garbuzov_p3_wallpapers.api.PhotosSearchApi
import com.vironit.garbuzov_p3_wallpapers.data.Photo
import com.vironit.garbuzov_p3_wallpapers.data.PhotosPagingSource
import com.vironit.garbuzov_p3_wallpapers.data.database.FavoritePhotosDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(
    private val photosSearchApi: PhotosSearchApi,
    private val favoritePhotosDatabase: FavoritePhotosDatabase
) {

    fun getPhotosSearchResults(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PhotosPagingSource(photosSearchApi, query) }
    ).liveData

    fun insertToFavorites(photo: Photo) =
        favoritePhotosDatabase.photoDao().insertToFavorites(photo)

    fun getFavoritePhotos() = favoritePhotosDatabase.photoDao().getFavoritePhotos()

    fun removeFromFavorites(photo: Photo) =
        favoritePhotosDatabase.photoDao().removeFromFavorites(photo)
}