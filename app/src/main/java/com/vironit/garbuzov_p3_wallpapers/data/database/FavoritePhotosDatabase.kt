package com.vironit.garbuzov_p3_wallpapers.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vironit.garbuzov_p3_wallpapers.data.Photo
import com.vironit.garbuzov_p3_wallpapers.di.AppModule

@Database(entities = [Photo::class], version = 1)
@TypeConverters(PhotoDataConverter::class)
abstract class FavoritePhotosDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var instance: FavoritePhotosDatabase? = null
        private val LOCK = Any()

        operator fun invoke() = instance ?: synchronized(LOCK) {
            instance ?: AppModule.provideDatabase(Application()).also {
                instance = it
                it.close()
            }
        }
    }
}