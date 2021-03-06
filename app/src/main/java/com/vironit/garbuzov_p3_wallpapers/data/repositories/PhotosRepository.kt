package com.vironit.garbuzov_p3_wallpapers.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vironit.garbuzov_p3_wallpapers.api.PhotosSearchApi
import com.vironit.garbuzov_p3_wallpapers.data.PhotosPagingSource
import com.vironit.garbuzov_p3_wallpapers.data.database.PhotosDatabase
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosRepository @Inject constructor(
    private val photosSearchApi: PhotosSearchApi, private val photosDatabase: PhotosDatabase
) {

    fun getPhotosSearchResults(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PhotosPagingSource(photosSearchApi, query) }
    ).liveData

    fun insertPhotoToFavorites(photo: Photo) =
        photosDatabase.photoDao().insertToFavorites(photo)

    fun insertSearchQuery(searchQuery: SearchQuery) =
        photosDatabase.searchQueryDao().insertSearchQuery(searchQuery)

    fun getFavoritePhotos() = photosDatabase.photoDao().getFavoritePhotos()

    fun getAllSearchQueries() = photosDatabase.searchQueryDao().getAllSearchQueries()

    fun getAllSearchQueriesValues() = photosDatabase.searchQueryDao().getAllSearchQueriesValues()

    fun addSearchQueryToFavorites(searchQuery: SearchQuery) {
        searchQuery.queryFavoriteFlag = true
        photosDatabase.searchQueryDao().addSearchQueryToFavorites(searchQuery)
    }

    fun getFavoriteSearchQueries() =
        photosDatabase.searchQueryDao().getFavoriteSearchQueries()

    fun removePhotoFromFavorites(photo: Photo) =
        photosDatabase.photoDao().removeFromFavorites(photo)

    fun removeSearchQueryFromFavorites(searchQuery: SearchQuery) =
        photosDatabase.searchQueryDao().removeSearchQueryFromFavorites(searchQuery)
}