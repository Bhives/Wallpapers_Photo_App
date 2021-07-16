package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
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
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentCurrentPhotoBinding
import com.vironit.garbuzov_p3_wallpapers.ui.bindingActivity
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoritePhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
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
        val bottomSheetBehavior = BottomSheetBehavior.from(photoInfoCard)
        bindingActivity.fragmentsMenu.isVisible = false
        attachPhotoAndInfo(photo)
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
                    showPhotoInfo(bottomSheetBehavior, photo)
                    true
                }
                else -> false
            }
        }
        onBackPressedAction(bottomSheetBehavior)
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

    private fun setWallpaper() {
        //val dialog = BottomSheetDialog(requireContext())
        //dialog.setCancelable(false)
        //dialog.setContentView(view)
        //dialog.show()
        //dialog.dismiss()
    }

    private fun showPhotoInfo(bottomSheetBehavior: BottomSheetBehavior<CardView>, photo: Photo) {
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

        photoInfoCard.portfolioButton.setOnClickListener {
            val portfolioUri = Uri.parse(photo.user.portfolioUrl)
            val portfolioIntent = Intent(Intent.ACTION_VIEW, portfolioUri)
            context?.startActivity(portfolioIntent)
        }

        binding.photoInfoHideButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun onBackPressedAction(bottomSheetBehavior: BottomSheetBehavior<CardView>) {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    } else {
                        findNavController().popBackStack()
                    }
                }
            })
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            photosFavoritesViewModel.insertToFavorites(args.photo)
        } else {
            photosFavoritesViewModel.removeFromFavorites(args.photo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        bindingActivity.fragmentsMenu.isVisible = true
    }
}