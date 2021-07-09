package com.vironit.garbuzov_p3_wallpapers.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vironit.garbuzov_p3_wallpapers.data.Photo
import dagger.Provides
import javax.inject.Inject

@Database(entities = [Photo::class], version = 1)
@TypeConverters(PhotoDataConverter::class)
abstract class FavoritePhotosDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    //companion object {
    //    @Volatile
    //    private var instance: FavoritePhotosDatabase? = null
    //    private val LOCK = Any()
    //
    //    operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
    //        instance ?: createDatabase(context).also {
    //            instance = it
    //        }
    //    }
    //
    //    private fun createDatabase(context: Context) =
    //        Room.databaseBuilder(
    //            context.applicationContext,
    //            FavoritePhotosDatabase::class.java,
    //            "favorite_photos.db"
    //        ).build()
    //}
}