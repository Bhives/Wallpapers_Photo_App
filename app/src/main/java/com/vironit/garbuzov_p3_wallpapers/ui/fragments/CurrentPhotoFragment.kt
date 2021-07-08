package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import java.io.IOException


class CurrentPhotoFragment : BaseFragment() {

    private val args by navArgs<CurrentPhotoFragmentArgs>()
    private var _binding: FragmentCurrentPhotoBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentCurrentPhotoBinding.inflate(inflater, container, false)
        attachPhoto()
        setWallpaper()
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_currentPhotoFragment_to_imageSearchFragment)
        }
        return binding.root
    }

    private fun attachPhoto() {
        binding.apply {
            val photo = args.photo
            Glide.with(this@CurrentPhotoFragment)
                .load(photo.urls.full)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(selectedPhotoImageView)
            photoDescriptionTextView.text = photo.description

            val uri = Uri.parse(photo.user.portfolioUrl)
            val portfolioIntent = Intent(Intent.ACTION_VIEW, uri)

            authorTextView.apply {
                text = "Photo by ${photo.user.username}"
                setOnClickListener {
                    context.startActivity(portfolioIntent)
                }
                paint.isUnderlineText = true
            }
        }
    }

    @SuppressLint("ResourceType")
    fun setWallpaper() {
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
        binding.setWallpaperButton.setOnClickListener {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
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
    }
}