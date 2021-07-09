package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.PhotoCardBinding

class FavoritePhotosAdapter(private val clickListener: OnItemClickListener, var photosList: List<Photo>) :
    RecyclerView.Adapter<FavoritePhotosAdapter.FavoritePhotosHolder>() {

    //lateinit var photosList: List<Photo>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePhotosHolder {
        val binding =
            PhotoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritePhotosHolder(binding)
    }

    override fun onBindViewHolder(favoritePhotosHolder: FavoritePhotosHolder, position: Int) {
        favoritePhotosHolder.bindPhoto(photosList[position])
    }

    inner class FavoritePhotosHolder(private val binding: PhotoCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = photosList[position]
                    clickListener.onItemClick(item)
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
                    .into(currentPhotoImageView)
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