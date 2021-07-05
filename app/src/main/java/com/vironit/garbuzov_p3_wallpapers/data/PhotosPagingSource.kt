package com.vironit.garbuzov_p3_wallpapers.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vironit.garbuzov_p3_wallpapers.api.PhotosSearchApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class PhotosPagingSource(private val photosSearchApi: PhotosSearchApi, private val query: String) :
    PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val currentPosition = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = photosSearchApi.searchPhotos(query, currentPosition, params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (currentPosition == UNSPLASH_STARTING_PAGE_INDEX) null else currentPosition - 1,
                nextKey = if (photos.isEmpty()) null else currentPosition + 1
            )
        } catch (iOException: IOException){
            LoadResult.Error(iOException)
        } catch (httpException: HttpException){
            LoadResult.Error(httpException)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        TODO("Not yet implemented")
    }
}