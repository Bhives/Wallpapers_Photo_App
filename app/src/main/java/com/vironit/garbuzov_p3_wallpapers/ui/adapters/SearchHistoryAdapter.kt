package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.SearchQueryCardBinding

class SearchHistoryAdapter(
    var searchQueriesList: List<SearchQuery>
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

    class SearchHistoryHolder(private val binding: SearchQueryCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindSearchQuery(searchQuery: SearchQuery) {
            binding.apply {
                queryTextView.text = searchQuery.queryText
                queryInfoTextView.text =
                    "${searchQuery.numberOfResults} results, ${searchQuery.lastUsed}"
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