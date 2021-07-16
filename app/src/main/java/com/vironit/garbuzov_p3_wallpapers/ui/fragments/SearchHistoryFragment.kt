package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentSearchHistoryBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.SearchHistoryAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.SearchHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_query_card.view.*

@AndroidEntryPoint
class SearchHistoryFragment : BaseFragment(R.layout.fragment_search_history),
    CompoundButton.OnCheckedChangeListener {

    private var _binding: FragmentSearchHistoryBinding? = null
    val binding get() = _binding!!
    private val searchHistoryViewModel by viewModels<SearchHistoryViewModel>()
    private val searchHistoryAdapter = SearchHistoryAdapter(listOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchHistoryBinding.bind(view)
        setAdapter(requireContext())
        //binding.searchHistoryRecyclerView.addToFavoritesButton.setOnCheckedChangeListener(this)
    }

    private fun setAdapter(
        context: Context
    ) {
        binding.apply {
            searchHistoryRecyclerView.setHasFixedSize(true)
            searchHistoryRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            searchHistoryRecyclerView.adapter = searchHistoryAdapter
        }
        searchHistoryViewModel.getAllSearchQueries().observe(viewLifecycleOwner, {
            searchHistoryAdapter.searchQueriesList = it
            searchQueriesList = it
        })
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val searchQuery = searchHistoryAdapter.searchQueriesList[searchHistoryAdapter.getPosition()]
        if (isChecked) {
            searchHistoryViewModel.addSearchQueryToFavorites(searchQuery)
        } else {
            searchHistoryViewModel.removeFromFavorites(searchQuery)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var searchQueriesList: List<SearchQuery> = listOf()
    }
}