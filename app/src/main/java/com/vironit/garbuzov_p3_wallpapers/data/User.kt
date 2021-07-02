package com.vironit.garbuzov_p3_wallpapers.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val userId: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "instagram_username") val instagramUsername: String,
    @ColumnInfo(name = "twitter_username") val twitterUsername: String,
    @ColumnInfo(name = "portfolio_url") val portfolioUrl: String,
    @ColumnInfo(name = "profile_image") val profileImage: ProfileImage,
    @ColumnInfo(name = "links") val links: Links
)