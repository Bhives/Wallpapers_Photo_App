package com.vironit.garbuzov_p3_wallpapers.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "search_query")
@Parcelize
data class SearchQuery(
    @ColumnInfo(name = "query_text") val queryText: String,
    @ColumnInfo(name = "number_of_results") val numberOfResults: Int,
    @ColumnInfo(name = "last_used") val lastUsed: String,
    @ColumnInfo(name = "query_favorite_flag") val queryFavoriteFlag: Boolean
) : Parcelable
