package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.PhotoCardBinding

class SearchPhotosAdapter(private val clickListenerPhotos: OnPhotosItemClickListener) :
    PagingDataAdapter<Photo, SearchPhotosAdapter.PhotosSearchHolder>(
        PHOTO_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosSearchHolder {
        val binding =
            PhotoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosSearchHolder(binding)
    }

    override fun onBindViewHolder(searchHolder: PhotosSearchHolder, position: Int) {
        val currentPhoto = getItem(position)
        if (currentPhoto != null) {
            searchHolder.bindPhoto(currentPhoto)
        }
    }

    inner class PhotosSearchHolder(private val binding: PhotoCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        clickListenerPhotos.onItemClick(item)
                    }
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

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem
        }
    }
}