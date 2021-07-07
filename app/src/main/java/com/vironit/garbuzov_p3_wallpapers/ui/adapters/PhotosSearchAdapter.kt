package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.PhotoCardBinding

class PhotosSearchAdapter : PagingDataAdapter<Photo, PhotosSearchAdapter.PhotosHolder>(
    PHOTO_COMPARATOR
) {

    lateinit var photosList: List<Photo>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosHolder {
        val binding =
            PhotoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosHolder, position: Int) {
        val currentPhoto = getItem(position)
        if (currentPhoto != null) {
            holder.bindPhoto(currentPhoto)
        }
    }

    class PhotosHolder(private val binding: PhotoCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindPhoto(photo: Photo) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerInside()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(currentPhoto)
                photoAuthor.text = photo.user.username
            }
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.photoId == newItem.photoId

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem
        }
    }
}