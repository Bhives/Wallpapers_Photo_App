package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.databinding.FragmentSearchHistoryBinding
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.SearchHistoryAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.SearchHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchHistoryFragment : BaseFragment() {

    private var _binding: FragmentSearchHistoryBinding? = null
    val binding get() = _binding!!
    private val searchHistoryViewModel by viewModels<SearchHistoryViewModel>()
    private val searchHistoryAdapter = SearchHistoryAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchHistoryBinding.inflate(inflater, container, false)
        setAdapter(requireContext())
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var searchQueriesList: List<SearchQuery> = listOf()
    }
}