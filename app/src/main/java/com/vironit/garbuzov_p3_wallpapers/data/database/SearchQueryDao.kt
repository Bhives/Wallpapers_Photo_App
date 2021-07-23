package com.vironit.garbuzov_p3_wallpapers.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery

@Dao
interface SearchQueryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchQuery(query: SearchQuery)

    @Query("SELECT * FROM search_query")
    fun getAllSearchQueries(): LiveData<List<SearchQuery>>

    @Query("SELECT query_text FROM search_query")
    fun getAllSearchQueriesValues(): LiveData<List<String>>

    @Update
    fun addSearchQueryToFavorites(searchQuery: SearchQuery)

    @Query("SELECT * FROM search_query WHERE query_favorite_flag=1")
    fun getFavoriteSearchQueries(): LiveData<List<SearchQuery>>

    @Update
    fun removeSearchQueryFromFavorites(searchQuery: SearchQuery)
}