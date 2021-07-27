package com.vironit.garbuzov_p3_wallpapers.ui.adapters.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FavoriteSearchQueryCardBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnSearchQueryItemClickListener
import com.vironit.garbuzov_p3_wallpapers.viewmodels.favorites.FavoriteSearchQueriesViewModel

class FavoriteSearchQueriesAdapter(
    private val favoriteSearchQueriesViewModel: FavoriteSearchQueriesViewModel,
    var searchQueriesList: MutableList<SearchQuery>,
    private val clickListenerFavoriteSearchQueries: OnSearchQueryItemClickListener
) :
    ListAdapter<SearchQuery, FavoriteSearchQueriesAdapter.FavoriteSearchQueriesHolder>(
        QUERY_COMPARATOR) {

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
        favoriteSearchQueriesHolder.bindSearchQuery(searchQueriesList[position])
        favoriteSearchQueriesHolder.itemView.animation =
            AnimationUtils.loadAnimation(
                favoriteSearchQueriesHolder.itemView.context,
                R.anim.recyclerview_items_animation
            )
    }

    inner class FavoriteSearchQueriesHolder(private val binding: FavoriteSearchQueryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickListenerFavoriteSearchQueries.onItemClick(searchQueriesList[position].queryText)
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
        return if (searchQueriesList.isNullOrEmpty()) {
            0
        } else {
            searchQueriesList.size
        }
    }

    fun deleteItem(searchQuery: SearchQuery) {
        favoriteSearchQueriesViewModel.removeFromFavorites(searchQuery)
        searchQueriesList.remove(searchQuery)
        notifyDataSetChanged()
    }

    companion object {
        private val QUERY_COMPARATOR = object : DiffUtil.ItemCallback<SearchQuery>() {
            override fun areItemsTheSame(oldItem: SearchQuery, newItem: SearchQuery) =
                oldItem.queryText == newItem.queryText

            override fun areContentsTheSame(oldItem: SearchQuery, newItem: SearchQuery) =
                oldItem == newItem
        }
    }
}