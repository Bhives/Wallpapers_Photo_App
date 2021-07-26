package com.vironit.garbuzov_p3_wallpapers.api

import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo

data class UnsplashResponse(
    val results: List<Photo>
)
