package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.SearchQueryCardBinding
import com.vironit.garbuzov_p3_wallpapers.viewmodels.SearchHistoryViewModel

class SearchHistoryAdapter(
    val searchHistoryViewModel: SearchHistoryViewModel,
    var searchQueriesList: List<SearchQuery>,
    private val clickListenerSearchQueries: OnSearchQueryItemClickListener
) :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        val binding =
            SearchQueryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryHolder(binding)
    }

    override fun onBindViewHolder(searchHistoryHolder: SearchHistoryHolder, position: Int) {
        searchHistoryHolder.bindSearchQuery(searchQueriesList[position])
    }

    inner class SearchHistoryHolder(private val binding: SearchQueryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickListenerSearchQueries.onItemClick(searchQueriesList[position])
                }
            }
        }

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
}