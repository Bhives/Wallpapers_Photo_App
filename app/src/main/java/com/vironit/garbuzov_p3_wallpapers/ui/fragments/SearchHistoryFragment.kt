package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentSearchHistoryBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnSearchQueryItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.SearchHistoryAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.SearchHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_query_card.view.*

@AndroidEntryPoint
class SearchHistoryFragment : BaseFragment(R.layout.fragment_search_history),
    OnSearchQueryItemClickListener{

    private var _binding: FragmentSearchHistoryBinding? = null
    val binding get() = _binding!!
    private val searchHistoryViewModel by viewModels<SearchHistoryViewModel>()
    private val searchHistoryAdapter = SearchHistoryAdapter(listOf(), this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchHistoryBinding.bind(view)
        setAdapter(requireContext())
        onCheckedChanged()
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

    private fun onCheckedChanged() {
        //val searchQuery = searchHistoryAdapter.searchQueriesList[searchHistoryAdapter.itemPosition]
        searchHistoryAdapter.passViewModelValue(searchHistoryViewModel)
    }

    override fun onItemClick(searchQuery: SearchQuery) {
        val action =
            SearchHistoryFragmentDirections.actionSearchHistoryFragmentToPhotosSearchFragment(searchQuery)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var searchQueriesList: List<SearchQuery> = listOf()
    }
}