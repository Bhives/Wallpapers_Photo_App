package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.CompoundButton
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.bindingActivity
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoritePhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.util.*


@AndroidEntryPoint
class CurrentPhotoFragment : BaseFragment(R.layout.fragment_current_photo),
    CompoundButton.OnCheckedChangeListener {

    private val args by navArgs<CurrentPhotoFragmentArgs>()
    private val photosFavoritesViewModel by viewModels<FavoritePhotosViewModel>()
    private var _binding: FragmentCurrentPhotoBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrentPhotoBinding.bind(view)
        bindingActivity.fragmentsMenu.isVisible = false
        attachPhoto()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        //binding.favoritesToggle.setOnCheckedChangeListener(this)

        binding.currentPhotoBottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.sharePhoto -> {
                    share()
                    true
                }
                R.id.wallpaperSet -> {
                    photosFavoritesViewModel.setWallpaper(
                        binding,
                        requireContext(),
                        this.requireActivity()
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun attachPhoto() {
        binding.apply {
            val photo = args.photo
            Glide.with(this@CurrentPhotoFragment)
                .load(photo.urls.full)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(selectedPhotoImageView)
            //photoDescriptionTextView.text = photo.description

            //if (photosFavoritesViewModel.photoIsInFavorites(photo.id)) {
            //    favoritesToggle.isChecked
            //} else {
            //    !favoritesToggle.isChecked
            //}
            //val uri = Uri.parse(photo.user.portfolioUrl)
            //val portfolioIntent = Intent(Intent.ACTION_VIEW, uri)
            //authorTextView.apply {
            //    text = "Photo by ${photo.user.username}"
            //    setOnClickListener {
            //        //context.startActivity(portfolioIntent)
            //    }
            //    paint.isUnderlineText = true
            //}
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            photosFavoritesViewModel.insertToFavorites(args.photo)
        } else {
            photosFavoritesViewModel.removeFromFavorites(args.photo)
        }
    }

    private fun share() {
        val photo = args.photo
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            100
        )
        Glide.with(this@CurrentPhotoFragment)
            .asBitmap()
            .load(photo.urls.full)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    var bitmap: Bitmap = resource
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_STREAM, getImageUri(requireContext(), bitmap))
                    intent.type = "image/*"
                    startActivity(Intent.createChooser(intent, "Share to: "))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })


    }

    fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        val path: String = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            UUID.randomUUID().toString() + ".png",
            "drawing"
        )
        return Uri.parse(path)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bindingActivity.fragmentsMenu.isVisible = true
    }
}