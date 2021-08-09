package com.vironit.garbuzov_p3_wallpapers.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "photo")
@Parcelize
data class Photo(
    @PrimaryKey @SerializedName ("id") @Expose var id: String,
    @SerializedName("created_at") @Expose val createdAt: String?,
    @SerializedName ("width") @Expose val width: Int,
    @SerializedName ("height") @Expose val height: Int,
    @SerializedName("color") @Expose val color: String?,
    @SerializedName("blur_hash") @Expose val blurHash: String?,
    @SerializedName("likes") @Expose val likes: Int,
    @SerializedName("liked_by_user") @Expose val likedByUser: Boolean,
    @SerializedName("description") @Expose val description: String?,
    @SerializedName("user") @Expose val user: User,
    @SerializedName("urls") @Expose val urls: Urls,
    @SerializedName("links") @Expose val links: Links
) : Parcelable {
    @Parcelize
    data class User(
        @SerializedName("user_id") @Expose val userId: String?,
        @SerializedName("username") @Expose val username: String?,
        @SerializedName("name") @Expose val name: String?,
        @SerializedName("first_name") @Expose val firstName: String?,
        @SerializedName("last_name") @Expose val lastName: String?,
        @SerializedName("instagram_username") @Expose val instagramUsername: String?,
        @SerializedName("twitter_username") @Expose val twitterUsername: String?,
        @SerializedName("profile_image") @Expose val profileImage: ProfileImage?,
        @SerializedName("links") @Expose val links: Links
    ) : Parcelable {
        val portfolioUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
        @Parcelize
        data class ProfileImage(
            @SerializedName("small") @Expose val small: String?,
            @SerializedName("medium") @Expose val medium: String?,
            @SerializedName("large") @Expose val large: String?
        ) : Parcelable

        @Parcelize
        data class Links(
            @SerializedName("self") @Expose val self: String?,
            @SerializedName("html") @Expose val html: String?,
            @SerializedName("photos") @Expose val photos: String?,
            @SerializedName("likes") @Expose val likes: String?
        ) : Parcelable
    }

    @Parcelize
    data class Urls(
        @SerializedName("raw") @Expose val raw: String?,
        @SerializedName("full") @Expose val full: String?,
        @SerializedName("regular") @Expose val regular: String?,
        @SerializedName("small") @Expose val small: String?,
        @SerializedName("thumb") @Expose val thumb: String?
    ) : Parcelable

    @Parcelize
    data class Links(
        @SerializedName("self") @Expose val self: String?,
        @SerializedName("html") @Expose val html: String?,
        @SerializedName("photos") @Expose val photos: String?,
        @SerializedName("likes") @Expose val likes: String?
    ) : Parcelable
}
