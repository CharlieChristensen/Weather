package com.example.pillarexcercise.homescreen

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pillarexcercise.R
import com.example.pillarexcercise.homescreen.SearchHistoryAdapter.SearchHistoryCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.cell_search_history.*
import android.text.style.UnderlineSpan
import android.text.SpannableString



class SearchHistoryViewHolder(override val containerView: View, private val callback: SearchHistoryCallback) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        itemView.setOnClickListener { callback.onItemClicked(adapterPosition) }
    }

    fun bind(listItem: SearchHistoryListItem){
        val title = when(listItem){
            is SearchHistoryListItem.City -> listItem.cityName
            is SearchHistoryListItem.ZipCode -> listItem.zipCode
            is SearchHistoryListItem.LatLong -> {
                itemView.context.getString(R.string.lat_long_title, listItem.latitude, listItem.longitude)
            }
        }
        val content = SpannableString(title)
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        titleTextView.text = content
    }

}