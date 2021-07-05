package com.vironit.garbuzov_p3_wallpapers.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vironit.garbuzov_p3_wallpapers.api.PhotosSearchApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(private val photosSearchApi: PhotosSearchApi) {

    fun getPhotosSearchResults(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {PhotosPagingSource(photosSearchApi, query)}
    ).liveData

    //fun insertToFavorites(photo: Photo) =
    //    photosDatabase.photoDao().insertPhoto(photo)

    //fun getFavoritePhotos() = photosDatabase.photoDao().getFavoritePhotos()
}