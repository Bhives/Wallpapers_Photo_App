package com.vironit.garbuzov_p3_wallpapers.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vironit.garbuzov_p3_wallpapers.data.database.PhotoDataConverter
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "photo")
@Parcelize
data class Photo(
    @PrimaryKey var photoId: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "blur_hash") val blurHash: String,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "liked_by_user") val likedByUser: Boolean,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "user") val user: User,
    @ColumnInfo(name = "urls") val urls: Urls,
    @ColumnInfo(name = "links") val links: Links
) : Parcelable {
    @Parcelize
    data class User(
        val userId: String,
        val username: String,
        val name: String,
        val firstName: String,
        val lastName: String,
        val instagramUsername: String,
        val twitterUsername: String,
        //val profileImage: ProfileImage,
        val links: Links
    ) : Parcelable {
        val portfolioUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
        @Parcelize
        data class ProfileImage(
            val small: String,
            val medium: String,
            val large: String
        ) : Parcelable

        @Parcelize
        data class Links(
            val self: String,
            val html: String,
            val photos: String,
            val likes: String
        ) : Parcelable
    }

    @Parcelize
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    ) : Parcelable

    @Parcelize
    data class Links(
        val self: String,
        val html: String,
        val photos: String,
        val likes: String
    ) : Parcelable
}
