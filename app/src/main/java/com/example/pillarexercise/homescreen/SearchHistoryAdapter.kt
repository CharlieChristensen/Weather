package com.example.pillarexcercise.homescreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.pillarexcercise.R

class SearchHistoryAdapter(val callback: SearchHistoryCallback) : ListAdapter<SearchHistoryListItem, SearchHistoryViewHolder>(DIFF) {

    interface SearchHistoryCallback {
        fun onItemClicked(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchHistoryViewHolder(inflater.inflate(R.layout.cell_search_history, parent, false), callback)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<SearchHistoryListItem>() {
            override fun areItemsTheSame(
                oldItem: SearchHistoryListItem,
                newItem: SearchHistoryListItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SearchHistoryListItem,
                newItem: SearchHistoryListItem
            ): Boolean = oldItem == newItem

        }
    }
}