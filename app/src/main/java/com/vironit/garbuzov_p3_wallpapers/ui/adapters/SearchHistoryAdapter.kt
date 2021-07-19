package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.SearchQueryCardBinding
import com.vironit.garbuzov_p3_wallpapers.viewmodels.SearchHistoryViewModel
import kotlinx.android.synthetic.main.search_query_card.view.*

class SearchHistoryAdapter(val searchHistoryViewModel: SearchHistoryViewModel,
    var searchQueriesList: List<SearchQuery>,
    private val clickListenerSearchQueries: OnSearchQueryItemClickListener
) :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder>() {

    var itemPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        val binding =
            SearchQueryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryHolder(binding)
    }

    override fun onBindViewHolder(searchHistoryHolder: SearchHistoryHolder, position: Int) {
        searchHistoryHolder.bindSearchQuery(searchQueriesList[position])
        searchHistoryHolder.itemView.addToFavoritesButton.setOnClickListener {
            itemPosition = position
        }
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

            binding.addToFavoritesButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    searchHistoryViewModel.addSearchQueryToFavorites(searchQueriesList[itemPosition])
                } else {
                    searchHistoryViewModel.removeFromFavorites(searchQueriesList[itemPosition])
                }
            }
        }

        fun bindSearchQuery(searchQuery: SearchQuery) {
            binding.apply {
                queryTextView.text = searchQuery.queryText
                queryInfoTextView.text =
                    "${searchQuery.numberOfResults} results, ${searchQuery.lastUsed}"
                if (searchQuery.queryFavoriteFlag) {
                    binding.addToFavoritesButton.isEnabled
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