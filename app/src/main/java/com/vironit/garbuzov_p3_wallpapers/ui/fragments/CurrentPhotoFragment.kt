package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.bindingActivity
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoritePhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.photo_information_sheet.*
import kotlinx.android.synthetic.main.photo_information_sheet.view.*
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
        val photo = args.photo
        bindingActivity.fragmentsMenu.isVisible = false
        attachPhoto(photo)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        //binding.favoritesToggle.setOnCheckedChangeListener(this)

        binding.currentPhotoBottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.sharePhoto -> {
                    share(photo)
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
                R.id.photoInformation -> {
                    showPhotoInfo()
                    true
                }
                else -> false
            }
        }
    }

    private fun attachPhoto(photo: Photo) {
        binding.apply {
            Glide.with(this@CurrentPhotoFragment)
                .load(photo.urls.full)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(selectedPhotoImageView)

            Glide.with(this@CurrentPhotoFragment)
                .load(photo.user.profileImage?.large)
                .error(R.drawable.ic_error)
                .into(photoInfoCard.userPhotoImageView)
            photoInfoCard.userNameTextView.text = "${photo.user.name}"

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

    private fun share(photo: Photo) {
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
                    intent.putExtra(
                        Intent.EXTRA_STREAM,
                        photosFavoritesViewModel.getImageUri(requireContext(), bitmap)
                    )
                    intent.type = "image/*"
                    startActivity(Intent.createChooser(intent, "Share to: "))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun showPhotoInfo() {
        val bottomSheetBehavior = BottomSheetBehavior.from(photoInfoCard)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.photoInfoHideButton.isVisible = false
                        binding.selectedPhotoToolbar.isVisible = true
                        bottomSheetBehavior.isDraggable = false
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        binding.selectedPhotoToolbar.isVisible = false
                        binding.photoInfoHideButton.isVisible = true
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
        binding.photoInfoHideButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehavior.setPeekHeight(0, true)
        }
    }

    private fun setWallpaper() {
        //val dialog = BottomSheetDialog(requireContext())
        //dialog.setCancelable(false)
        //dialog.setContentView(view)
        //dialog.show()
        //dialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bindingActivity.fragmentsMenu.isVisible = true
    }
}