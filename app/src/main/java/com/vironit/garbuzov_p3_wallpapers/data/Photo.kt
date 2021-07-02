package com.vironit.garbuzov_p3_wallpapers.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey val photoId: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "blur_hash") val blurHash: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "liked_by_user") val likedByUser: Boolean,
    @ColumnInfo(name = "user") val user: User,
    @ColumnInfo(name = "urls") val urls: Urls,
    @ColumnInfo(name = "links") val links: Links
)
