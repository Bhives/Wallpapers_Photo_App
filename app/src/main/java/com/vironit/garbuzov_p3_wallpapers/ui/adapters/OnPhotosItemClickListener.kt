package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo

interface OnPhotosItemClickListener {

    fun onItemClick (photo: Photo)
}