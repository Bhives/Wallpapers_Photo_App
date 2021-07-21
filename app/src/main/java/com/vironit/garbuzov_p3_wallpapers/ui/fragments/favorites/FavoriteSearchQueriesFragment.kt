package com.vironit.garbuzov_p3_wallpapers.ui.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentFavoriteSearchQueriesBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnSearchQueryItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites.FavoriteSearchQueriesAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.fragments.FavoritesFragmentDirections
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoriteSearchQueriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteSearchQueriesFragment : BaseFragment(R.layout.fragment_favorite_search_queries),
    OnSearchQueryItemClickListener {

    private var _binding: FragmentFavoriteSearchQueriesBinding? = null
    val binding get() = _binding!!
    private val favoriteSearchQueriesViewModel by viewModels<FavoriteSearchQueriesViewModel>()
    private lateinit var favoriteSearchQueriesAdapter: FavoriteSearchQueriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteSearchQueriesBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        favoriteSearchQueriesAdapter =
            FavoriteSearchQueriesAdapter(favoriteSearchQueriesViewModel, mutableListOf(), this)
        binding.apply {
            favoriteQueriesRecyclerView.adapter = favoriteSearchQueriesAdapter
        }
        favoriteSearchQueriesViewModel.getFavoriteSearchQueries().observe(viewLifecycleOwner, {
            favoriteSearchQueriesAdapter.favoriteSearchQueriesList = it.toMutableList()
        })
    }

    override fun onItemClick(searchQuery: SearchQuery) {
        val action =
            FavoritesFragmentDirections.actionFavoritesFragmentToPhotosSearchFragment(
                searchQuery
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}