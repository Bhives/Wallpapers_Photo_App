package com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FavoriteSearchQueryCardBinding

class FavoriteSearchQueriesAdapter(
    var favoriteSearchQueriesList: List<SearchQuery>
) :
    RecyclerView.Adapter<FavoriteSearchQueriesAdapter.FavoriteSearchQueriesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteSearchQueriesHolder {
        val binding =
            FavoriteSearchQueryCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FavoriteSearchQueriesHolder(binding)
    }

    override fun onBindViewHolder(
        favoriteSearchQueriesHolder: FavoriteSearchQueriesHolder,
        position: Int
    ) {
        favoriteSearchQueriesHolder.bindSearchQuery(favoriteSearchQueriesList[position])
    }

    class FavoriteSearchQueriesHolder(private val binding: FavoriteSearchQueryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindSearchQuery(searchQuery: SearchQuery) {
            binding.apply {
                favoriteQueryTextView.text = searchQuery.queryText
                favoriteQueryInfoTextView.text =
                    "${searchQuery.numberOfResults} results, ${searchQuery.lastUsed}"
            }
        }
    }

    override fun getItemCount(): Int {
        return if (favoriteSearchQueriesList.isNullOrEmpty()) {
            0
        } else {
            favoriteSearchQueriesList.size
        }
    }
}