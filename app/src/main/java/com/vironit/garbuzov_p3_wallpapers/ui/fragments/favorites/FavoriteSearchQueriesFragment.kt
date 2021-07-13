package com.vironit.garbuzov_p3_wallpapers.ui.fragments.favorites

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentFavoriteSearchQueriesBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites.FavoriteSearchQueriesAdapter
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoriteSearchQueriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteSearchQueriesFragment : Fragment() {

    private var _binding: FragmentFavoriteSearchQueriesBinding? = null
    val binding get() = _binding!!
    private val favoriteSearchQueriesViewModel by viewModels<FavoriteSearchQueriesViewModel>()
    private val favoriteSearchQueriesAdapter = FavoriteSearchQueriesAdapter(listOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteSearchQueriesBinding.bind(view)
        setAdapter(requireContext())
    }

    private fun setAdapter(
        context: Context
    ) {
        binding.apply {
            favoriteQueriesRecyclerView.setHasFixedSize(true)
            favoriteQueriesRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            favoriteQueriesRecyclerView.adapter = favoriteSearchQueriesAdapter
        }
        favoriteSearchQueriesViewModel.getFavoriteSearchQueries().observe(viewLifecycleOwner, {
            favoriteSearchQueriesAdapter.favoriteSearchQueriesList = it
            favoriteSearchQueriesList = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var favoriteSearchQueriesList: List<SearchQuery> = listOf()
    }
}