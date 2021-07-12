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

    @Query("SELECT * FROM search_query WHERE query_text=:queryText")
    fun getFavoriteSearchQueries(queryText: String): LiveData<SearchQuery>

    @Delete
    fun removeSearchQueryFromFavorites(searchQuery: SearchQuery)
}