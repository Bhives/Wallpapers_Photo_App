package com.vironit.garbuzov_p3_wallpapers.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vironit.garbuzov_p3_wallpapers.data.Photo

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToFavorites(photo: Photo)

    @Query("SELECT * FROM photo")
    fun getFavoritePhotos(): LiveData<List<Photo>>
}