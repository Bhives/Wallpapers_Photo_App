package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.bindingActivity
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoritePhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_current_photo.*
import kotlinx.android.synthetic.main.photo_information_sheet.*
import kotlinx.android.synthetic.main.photo_information_sheet.view.*
import java.util.*

@AndroidEntryPoint
class CurrentPhotoFragment :
    BaseFragment<FragmentCurrentPhotoBinding>(R.layout.fragment_current_photo) {

    private val args by navArgs<CurrentPhotoFragmentArgs>()
    private val photosFavoritesViewModel by viewModels<FavoritePhotosViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo = args.photo
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetBehavior = BottomSheetBehavior.from(photoInfoCard)
        bindingActivity.fragmentsMenu.isVisible = false
        attachPhotoAndInfo(photo)
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        currentPhotoBottomMenu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.sharePhoto -> {
                    share(photo)
                    true
                }
                R.id.wallpaperSet -> {
                    setPhotoAction(bottomSheetDialog, photo)
                    true
                }
                R.id.photoInformation -> {
                    showPhotoInfo(bottomSheetBehavior, photo)
                    true
                }
                else -> false
            }
        }
        onBackPressedAction(bottomSheetDialog, bottomSheetBehavior)
    }

    private fun attachPhotoAndInfo(photo: Photo) {
        binding.apply {
            Glide.with(this@CurrentPhotoFragment)
                .load(photo.urls.full)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(selectedPhotoImageView)
        }
        photoInfoCard.apply {
            Glide.with(photoInfoCard)
                .load(photo.user.profileImage?.small)
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_error)
                .into(userPhotoImageView)
            userNameTextView.text = photo.user.name
            userLoginTextView.text = "@${photo.user.username}"
            userInstagramLoginTextView.text = photo.user.instagramUsername
            userTwitterLoginTextView.text = photo.user.twitterUsername
            photoDateTextView.text = photo.createdAt.toString()
            photoColorTextView.text = photo.color
            photoDimensionsTextView.text = "${photo.width}x${photo.height}"
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
                    val bitmap: Bitmap = resource
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_STREAM,
                        photosFavoritesViewModel.getImageUri(requireContext(), bitmap)
                    )
                    intent.type = "image/*"
                    startActivity(Intent.createChooser(intent, "Share with: "))
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }

    private fun setPhotoAction(bottomSheetDialog: BottomSheetDialog, photo: Photo) {
        bottomSheetDialog.setContentView(R.layout.bottom_photo_actions_dialog)
        bottomSheetDialog.show()
        bottomSheetDialog.findViewById<LinearLayout>(R.id.wallpaperOption)?.setOnClickListener {
            photosFavoritesViewModel.setPhotoAs(
                selectedPhotoImageView,
                requireContext(),
                requireActivity(),
                0
            )
            bottomSheetDialog.hide()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.lockScreenOption)?.setOnClickListener {
            photosFavoritesViewModel.setPhotoAs(
                selectedPhotoImageView,
                requireContext(),
                requireActivity(),
                1
            )
            bottomSheetDialog.hide()
        }
        bottomSheetDialog.findViewById<LinearLayout>(R.id.favoritesOption)?.setOnClickListener {
            if (photosFavoritesViewModel.photoIsInFavorites(photo)) {
                photosFavoritesViewModel.removeFromFavorites(photo)
                Toast.makeText(context, "Photo removed from favorites", Toast.LENGTH_SHORT).show()
            } else {
                photosFavoritesViewModel.insertToFavorites(photo)
                Toast.makeText(context, "Photo added to favorites", Toast.LENGTH_SHORT).show()
            }
            bottomSheetDialog.hide()
        }
    }

    private fun showPhotoInfo(bottomSheetBehavior: BottomSheetBehavior<CardView>, photo: Photo) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (bottomSheetBehavior.state) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        photoInfoHideButton.isVisible = false
                        selectedPhotoToolbar.isVisible = true
                        bottomSheetBehavior.isDraggable = false
                        currentPhotoBottomMenu.isVisible = true
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        selectedPhotoToolbar.isVisible = false
                        photoInfoHideButton.isVisible = true
                        currentPhotoBottomMenu.isVisible = false
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
        photoInfoCard.portfolioButton.setOnClickListener {
            findNavController().navigate(
                CurrentPhotoFragmentDirections.actionCurrentPhotoFragmentToAuthorPortfolioFragment(
                    photo.user.portfolioUrl
                )
            )
        }
        photoInfoHideButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun onBackPressedAction(
        bottomSheetDialog: BottomSheetDialog,
        bottomSheetBehavior: BottomSheetBehavior<CardView>
    ) {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when {
                        bottomSheetDialog.isShowing -> {
                            bottomSheetDialog.hide()
                        }
                        bottomSheetBehavior.state == BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                        else -> {
                            findNavController().popBackStack()
                        }
                    }
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingActivity.fragmentsMenu.isVisible = true
    }
}