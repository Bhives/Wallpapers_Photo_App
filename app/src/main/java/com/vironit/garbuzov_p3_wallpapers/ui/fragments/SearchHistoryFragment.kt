package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentSearchHistoryBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnSearchQueryItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.SearchHistoryAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.SearchHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchHistoryFragment : BaseFragment(R.layout.fragment_search_history),
    OnSearchQueryItemClickListener {

    private var _binding: FragmentSearchHistoryBinding? = null
    val binding get() = _binding!!
    private val searchHistoryViewModel by viewModels<SearchHistoryViewModel>()
    lateinit var searchHistoryAdapter: SearchHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchHistoryBinding.inflate(inflater, container, false)
        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        searchHistoryAdapter = SearchHistoryAdapter(searchHistoryViewModel, listOf(), this)
        binding.apply {
            searchHistoryRecyclerView.setHasFixedSize(false)
            searchHistoryRecyclerView.adapter = searchHistoryAdapter
        }
        searchHistoryViewModel.getAllSearchQueries().observe(viewLifecycleOwner, {
            searchHistoryAdapter.searchQueriesList = it
        })
    }

    override fun onItemClick(searchQuery: SearchQuery) {
        val action =
            SearchHistoryFragmentDirections.actionSearchHistoryFragmentToPhotosSearchFragment(
                searchQuery
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}