package com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites

import android.annotation.SuppressLint
import android.app.Activity
import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
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

    fun removeFromFavorites(photo: Photo) {
        viewModelScope.launch(Dispatchers.IO) {
            photosRepository.removePhotoFromFavorites(photo)
        }
    }

    fun photoIsInFavorites(photo: Photo): Boolean {
        return getFavoritePhotos().value?.contains(photo)==true
    }

    fun setPhotoAs(
        binding: FragmentCurrentPhotoBinding,
        context: Context,
        activity: Activity,
        setupOption: Int
    ) {
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.SET_WALLPAPER),
            100
        )
        try {
            binding.selectedPhotoImageView.buildDrawingCache()
            val bitmap: Bitmap = binding.selectedPhotoImageView.drawingCache
            when (setupOption) {
                0 -> {
                    wallpaperManager.setBitmap(bitmap)
                    Toast.makeText(context, "New wallpaper successfully set", Toast.LENGTH_SHORT)
                        .show()
                }
                1 -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                    }
                    Toast.makeText(context, "New lock screen successfully set", Toast.LENGTH_SHORT)
                        .show()
                }
            }
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