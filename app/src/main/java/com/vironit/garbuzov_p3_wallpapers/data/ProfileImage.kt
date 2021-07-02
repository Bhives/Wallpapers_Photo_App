package com.vironit.garbuzov_p3_wallpapers.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "profile_image")
data class ProfileImage(
    @ColumnInfo(name = "small") val small: String,
    @ColumnInfo(name = "medium") val medium: String,
    @ColumnInfo(name = "large") val large: String
)
