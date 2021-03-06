package com.vironit.garbuzov_p3_wallpapers.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.di.AppModule

@Database(entities = [Photo::class, SearchQuery::class], version = 1)
@TypeConverters(PhotoDataConverter::class)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun searchQueryDao(): SearchQueryDao

    companion object {
        @Volatile
        private var instance: PhotosDatabase? = null
        private val LOCK = Any()

        operator fun invoke() = instance ?: synchronized(LOCK) {
            instance ?: AppModule.provideDatabase(Application()).also {
                instance = it
                it.close()
            }
        }
    }
}