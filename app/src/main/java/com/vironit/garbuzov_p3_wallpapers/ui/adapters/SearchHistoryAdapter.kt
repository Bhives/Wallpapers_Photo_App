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
import kotlinx.android.synthetic.main.search_query_card.view.*

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
        val currentItem = getItem(position)
        if (currentItem != null) {
            searchHistoryHolder.bindSearchQuery(currentItem)
        }
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
                    val item = getItem(position)
                    if (item != null) {
                        clickListenerSearchQueries.onItemClick(item.queryText)
                    }
                }
            }
            binding.root.addToFavoritesButton.setOnCheckedChangeListener { _, isChecked ->
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        if (isChecked) {
                            searchHistoryViewModel.addSearchQueryToFavorites(item)
                        } else {
                            searchHistoryViewModel.removeFromFavorites(item)
                        }
                    }
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