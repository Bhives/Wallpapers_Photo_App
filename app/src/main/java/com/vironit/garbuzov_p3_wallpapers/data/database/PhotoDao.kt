package com.vironit.garbuzov_p3_wallpapers.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vironit.garbuzov_p3_wallpapers.data.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToFavorites(photo: Photo)

    @Query("SELECT * FROM photo")
    fun getFavoritePhotos(): LiveData<List<Photo>>

    @Query("SELECT * FROM photo WHERE id=:photoId")
    fun getFavoritePhoto(photoId: String): LiveData<Photo>

    @Delete
    fun removeFromFavorites(photo: Photo)
}