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
){
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
    ){
        @Entity(tableName = "profile_image")
        data class ProfileImage(
            @ColumnInfo(name = "small") val small: String,
            @ColumnInfo(name = "medium") val medium: String,
            @ColumnInfo(name = "large") val large: String
        )

        @Entity(tableName = "links")
        data class Links(
            @ColumnInfo(name = "self") val self: String,
            @ColumnInfo(name = "html") val html: String,
            @ColumnInfo(name = "photos") val photos: String,
            @ColumnInfo(name = "likes") val likes: String
        )
    }
    @Entity(tableName = "urls")
    data class Urls(
        @ColumnInfo(name = "raw") val raw: String,
        @ColumnInfo(name = "full") val full: String,
        @ColumnInfo(name = "regular") val regular: String,
        @ColumnInfo(name = "small") val small: String,
        @ColumnInfo(name = "thumb") val thumb: String
    )
    @Entity(tableName = "links")
    data class Links(
        @ColumnInfo(name = "self") val self: String,
        @ColumnInfo(name = "html") val html: String,
        @ColumnInfo(name = "photos") val photos: String,
        @ColumnInfo(name = "likes") val likes: String
    )
}
