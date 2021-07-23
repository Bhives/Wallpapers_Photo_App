package com.vironit.garbuzov_p3_wallpapers.ui.fragments

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.vironit.garbuzov_p3_wallpapers.R
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.Photo
import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.OnPhotosItemClickListener
import com.vironit.garbuzov_p3_wallpapers.ui.adapters.SearchPhotosAdapter
import com.vironit.garbuzov_p3_wallpapers.ui.templates.BaseFragment
import com.vironit.garbuzov_p3_wallpapers.viewmodels.PhotosSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photo_search.*

@AndroidEntryPoint
class PhotosSearchFragment : BaseFragment(R.layout.fragment_photo_search),
    OnPhotosItemClickListener {

    private val args by navArgs<PhotosSearchFragmentArgs>()
    override val viewModel by viewModels<PhotosSearchViewModel>()
    lateinit var photosSearchAdapter: SearchPhotosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photosSearchAdapter = SearchPhotosAdapter(this)
        setAdapter()
        //if (args.searchQuery!=null) {
        //    searchPhotoWithArgs()
        //    Toast.makeText(context, "searchPhotoWithArgs", Toast.LENGTH_SHORT).show()
        //}
        searchPhotos()
        toggleColumnsButton.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                photosRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            } else {
                photosRecyclerView.layoutManager =
                    StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            }
        }
    }

    private fun setAdapter() {
        photosRecyclerView.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        viewModel.photosAll.observe(viewLifecycleOwner) {
            photosSearchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        photosRecyclerView.adapter = photosSearchAdapter
    }

    private fun searchPhotos() {
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.queryLabel)
        val cursorAdapter = SimpleCursorAdapter(
            context,
            R.layout.query_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        var suggestions = listOf<String>()
        viewModel.getAllSearchQueriesValues().observe(viewLifecycleOwner) {
            suggestions = it
        }
        photoSearchView.suggestionsAdapter = cursorAdapter
        photoSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                photoSearchView.clearFocus()
                viewModel.insertSearchQuery(
                    SearchQuery(
                        query.toString(),
                        photosSearchAdapter.itemCount,
                        "",
                        false
                    )
                )
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    photosRecyclerView.smoothScrollToPosition(0)
                    viewModel.searchPhotos(query)
                }
                val cursor =
                    MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(query, true))
                            cursor.addRow(arrayOf(index, suggestion))
                    }
                }
                cursorAdapter.changeCursor(cursor)
                return true
            }
        })
        photoSearchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                photoSearchView.clearFocus()
                val cursor = photoSearchView.suggestionsAdapter.getItem(position) as Cursor
                val selection =
                    cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                photoSearchView.setQuery(selection, false)
                viewModel.searchPhotos(selection)
                return true
            }
        })
    }

    fun searchPhotoWithArgs() {
        photosRecyclerView.smoothScrollToPosition(0)
        photoSearchView.setQuery(args.searchQuery, true)
    }

    override fun onItemClick(photo: Photo) {
        val action =
            PhotosSearchFragmentDirections.actionPhotosSearchFragmentToCurrentPhotoFragment(photo)
        findNavController().navigate(action)
    }
}