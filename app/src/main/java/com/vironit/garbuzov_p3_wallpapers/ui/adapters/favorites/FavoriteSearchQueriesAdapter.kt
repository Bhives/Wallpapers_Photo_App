package com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FavoriteSearchQueryCardBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnSearchQueryItemClickListener
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoriteSearchQueriesViewModel

class FavoriteSearchQueriesAdapter(
    private val favoriteSearchQueriesViewModel: FavoriteSearchQueriesViewModel,
    var favoriteSearchQueriesList: MutableList<SearchQuery>,
    private val clickListenerFavoriteSearchQueries: OnSearchQueryItemClickListener
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

    inner class FavoriteSearchQueriesHolder(private val binding: FavoriteSearchQueryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickListenerFavoriteSearchQueries.onItemClick(favoriteSearchQueriesList[position])
                }
            }
        }

        fun bindSearchQuery(searchQuery: SearchQuery) {
            binding.apply {
                favoriteQueryTextView.text = searchQuery.queryText
                favoriteQueryInfoTextView.text =
                    "${searchQuery.numberOfResults} results, ${searchQuery.lastUsed}"
                removeFromFavoritesButton.setOnClickListener {
                    deleteItem(searchQuery)
                }
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

    fun deleteItem(searchQuery: SearchQuery) {
        favoriteSearchQueriesViewModel.removeFromFavorites(searchQuery)
        favoriteSearchQueriesList.remove(searchQuery)
        notifyDataSetChanged()
    }
}