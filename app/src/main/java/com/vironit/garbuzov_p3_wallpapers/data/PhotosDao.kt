package com.vironit.garbuzov_p3_wallpapers.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToFavorites(photo: Photo)

    @Query("SELECT * FROM photo")
    fun getFavoritePhotos(): LiveData<List<Photo>>
}