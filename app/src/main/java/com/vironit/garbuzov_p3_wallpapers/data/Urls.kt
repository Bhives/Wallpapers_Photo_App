package com.vironit.garbuzov_p3_wallpapers.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "urls")
data class Urls(
    @ColumnInfo(name = "raw") val raw: String,
    @ColumnInfo(name = "full") val full: String,
    @ColumnInfo(name = "regular") val regular: String,
    @ColumnInfo(name = "small") val small: String,
    @ColumnInfo(name = "thumb") val thumb: String
)