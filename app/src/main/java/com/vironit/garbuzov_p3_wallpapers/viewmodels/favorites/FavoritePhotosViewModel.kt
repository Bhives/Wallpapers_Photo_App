package com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites

import android.annotation.SuppressLint
import android.app.Activity
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.data.repositories.PhotosRepository
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    BaseViewModel() {

    fun insertToFavorites(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepository.insertPhotoToFavorites(photo)
        }
    }

    fun getFavoritePhotos() = photosRepository.getFavoritePhotos()

    private fun getFavoritePhoto(photoId: String) = photosRepository.getFavoritePhoto(photoId)

    fun removeFromFavorites(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepository.removePhotoFromFavorites(photo)
        }
    }

    fun photoIsInFavorites(photoId: String): Boolean {
        return getFavoritePhoto(photoId) == null
    }

    @SuppressLint("ResourceType")
    fun setWallpaper(binding: FragmentCurrentPhotoBinding, context: Context, activity: Activity) {
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.SET_WALLPAPER),
            100
        )
        try {
            binding.selectedPhotoImageView.buildDrawingCache()
            val bitmap: Bitmap = binding.selectedPhotoImageView.drawingCache
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(context, "New wallpaper successfully set", Toast.LENGTH_SHORT)
                .show()
        } catch (iOException: IOException) {
            iOException.printStackTrace()
        }
    }

    fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            UUID.randomUUID().toString() + ".png",
            "drawing"
        )
        return Uri.parse(path)
    }
}