package com.vironit.garbuzov_p3_wallpapers.ui.adapters

import com.vironit.garbuzov_p3_wallpapers.data.database.entities.SearchQuery

interface OnSearchQueryItemClickListener {

    fun onItemClick(searchQuery: SearchQuery)
}