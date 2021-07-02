package com.vironit.garbuzov_p3_wallpapers.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "links")
data class Links(
    @ColumnInfo(name = "self") val self: String,
    @ColumnInfo(name = "html") val html: String,
    @ColumnInfo(name = "photos") val photos: String,
    @ColumnInfo(name = "likes") val likes: String
)