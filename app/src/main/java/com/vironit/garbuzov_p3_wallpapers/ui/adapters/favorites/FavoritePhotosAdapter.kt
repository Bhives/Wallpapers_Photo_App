package com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.FavoritePhotoCardBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnPhotosItemClickListener

class FavoritePhotosAdapter(
    val clickListenerPhotos: OnPhotosItemClickListener,
    var photosList: List<Photo>
) :
    RecyclerView.Adapter<FavoritePhotosAdapter.FavoritePhotosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePhotosHolder {
        val binding =
            FavoritePhotoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritePhotosHolder(binding)
    }

    override fun onBindViewHolder(favoritePhotosHolder: FavoritePhotosHolder, position: Int) {
        favoritePhotosHolder.bindPhoto(photosList[position])
        favoritePhotosHolder.itemView.animation =
            AnimationUtils.loadAnimation(
                favoritePhotosHolder.itemView.context,
                R.anim.recyclerview_items_animation
            )
    }

    inner class FavoritePhotosHolder(private val binding: FavoritePhotoCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = photosList[position]
                    clickListenerPhotos.onItemClick(item)
                }
            }
        }

        fun bindPhoto(photo: Photo) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(favoritePhotoImageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (photosList.isNullOrEmpty()) {
            0
        } else {
            photosList.size
        }
    }
}