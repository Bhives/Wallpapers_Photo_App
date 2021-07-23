package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnSearchQueryItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.SearchHistoryAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.SearchHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_history.*

@AndroidEntryPoint
class SearchHistoryFragment : BaseFragment(R.layout.fragment_search_history),
    OnSearchQueryItemClickListener {

    override val viewModel by viewModels<SearchHistoryViewModel>()
    lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        searchHistoryAdapter = SearchHistoryAdapter(viewModel, listOf(), this)
        viewModel.getAllSearchQueries().observe(viewLifecycleOwner, {
            searchHistoryAdapter.searchQueriesList = it
        })
        searchHistoryRecyclerView.adapter = searchHistoryAdapter
    }

    override fun onItemClick(searchQueryText: String) {
        val action =
            SearchHistoryFragmentDirections.actionSearchHistoryFragmentToPhotosSearchFragment(
                searchQueryText
            )
        findNavController().navigate(action)
        //PhotosSearchFragment().searchPhotoWithArgs()
    }
}