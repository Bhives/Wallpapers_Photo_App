package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.SearchQueryCardBinding
import com.vironit.garbuzov_p3_wallpapers.viewmodels.SearchHistoryViewModel

class SearchHistoryAdapter(
    val searchHistoryViewModel: SearchHistoryViewModel,
    var searchQueriesList: List<SearchQuery>,
    private val clickListenerSearchQueries: OnSearchQueryItemClickListener
) :
    ListAdapter<SearchQuery, SearchHistoryAdapter.SearchHistoryHolder>(QUERY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        val binding =
            SearchQueryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryHolder(binding)
    }

    override fun onBindViewHolder(searchHistoryHolder: SearchHistoryHolder, position: Int) {
        searchHistoryHolder.bindSearchQuery(searchQueriesList[position])
        searchHistoryHolder.itemView.animation =
            AnimationUtils.loadAnimation(
                searchHistoryHolder.itemView.context,
                R.anim.recyclerview_items_animation
            )
    }

    inner class SearchHistoryHolder(private val binding: SearchQueryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickListenerSearchQueries.onItemClick(searchQueriesList[position].queryText)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bindSearchQuery(searchQuery: SearchQuery) {
            binding.apply {
                queryTextView.text = searchQuery.queryText
                queryInfoTextView.text =
                    "${searchQuery.numberOfResults} results, ${searchQuery.lastUsed}"
                if (searchQuery.queryFavoriteFlag) {
                    addToFavoritesButton.isChecked = true
                }
                addToFavoritesButton.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        searchHistoryViewModel.addSearchQueryToFavorites(searchQuery)
                    } else {
                        searchHistoryViewModel.removeFromFavorites(searchQuery)
                    }
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

    companion object {
        private val QUERY_COMPARATOR = object : DiffUtil.ItemCallback<SearchQuery>() {
            override fun areItemsTheSame(oldItem: SearchQuery, newItem: SearchQuery) =
                oldItem.queryText == newItem.queryText

            override fun areContentsTheSame(oldItem: SearchQuery, newItem: SearchQuery) =
                oldItem == newItem
        }
    }
}