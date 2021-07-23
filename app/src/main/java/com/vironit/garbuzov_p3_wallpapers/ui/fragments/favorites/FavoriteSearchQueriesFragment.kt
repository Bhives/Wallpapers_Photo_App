package com.vironit.garbuzov_p3_wallpapers.ui.fragments.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnSearchQueryItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites.FavoriteSearchQueriesAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.fragments.FavoritesFragmentDirections
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoriteSearchQueriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_search_queries.*

@AndroidEntryPoint
class FavoriteSearchQueriesFragment : BaseFragment(R.layout.fragment_favorite_search_queries),
    OnSearchQueryItemClickListener {

    override val viewModel by viewModels<FavoriteSearchQueriesViewModel>()
    private lateinit var favoriteSearchQueriesAdapter: FavoriteSearchQueriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        favoriteSearchQueriesAdapter =
            FavoriteSearchQueriesAdapter(viewModel, mutableListOf(), this)
        viewModel.getFavoriteSearchQueries().observe(viewLifecycleOwner, {
            favoriteSearchQueriesAdapter.searchQueriesList = it.toMutableList()
        })
        favoriteQueriesRecyclerView.adapter = favoriteSearchQueriesAdapter
    }

    override fun onItemClick(searchQueryText: String) {
        val action =
            FavoritesFragmentDirections.actionFavoritesFragmentToPhotosSearchFragment(
                searchQueryText
            )
        findNavController().navigate(action)
    }
}