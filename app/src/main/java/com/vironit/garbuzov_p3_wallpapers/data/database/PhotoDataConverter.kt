package com.vironit.garbuzov_p3_wallpapers.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vironit.garbuzov_p3_wallpapers.data.Photo
import java.lang.reflect.Type

class PhotoDataConverter {

    @TypeConverter
    fun fromUser(user: Photo.User): String? {
        if (user == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Photo.User>() {}.type
        return gson.toJson(user, type)
    }

    @TypeConverter
    fun toUser(user: String?): Photo.User? {
        if (user == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Photo.User>() {}.type
        return gson.fromJson<Photo.User>(user, type)
    }

    @TypeConverter
    fun fromUrls(urls: Photo.Urls): String? {
        if (urls == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Photo.Urls>() {}.type
        return gson.toJson(urls, type)
    }

    @TypeConverter
    fun toUrls(urls: String?): Photo.Urls? {
        if (urls == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Photo.Urls>() {}.type
        return gson.fromJson<Photo.Urls>(urls, type)
    }

    @TypeConverter
    fun fromLinks(links: Photo.Links): String? {
        if (links == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Photo.Links>() {}.type
        return gson.toJson(links, type)
    }

    @TypeConverter
    fun toLinks(links: String?): Photo.Links? {
        if (links == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<Photo.Links>() {}.type
        return gson.fromJson<Photo.Links>(links, type)
    }
}