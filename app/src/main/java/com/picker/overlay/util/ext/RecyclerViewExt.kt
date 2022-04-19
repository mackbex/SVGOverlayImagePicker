package com.picker.overlay.util.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.R
import com.picker.overlay.util.Resource
import com.picker.overlay.domain.model.SearchResult
import com.picker.overlay.domain.model.Theme
import com.picker.overlay.ui.company.horizontal_theme.HorizontalThemeAdapter
import com.picker.overlay.ui.company.search.SearchResultAdapter


/**
 * Recyclerview databinding Ext
 */

@BindingAdapter("items")
fun bindItems(recyclerView: RecyclerView, items: Resource<SearchResult>?) {
    val adapter = recyclerView.adapter as SearchResultAdapter
    when(items) {
        is Resource.Success -> {
            adapter.submitList(items.data.items)
        }
        is Resource.Failure -> {
            Snackbar.make(recyclerView.rootView, recyclerView.context.getString(R.string.err_failed_load_data), Snackbar.LENGTH_SHORT).show()
        }
    }
}

@BindingAdapter("theme_items")
fun bindThemeItems(recyclerView: RecyclerView, items: List<Theme>?) {
    val adapter = recyclerView.adapter as HorizontalThemeAdapter
    adapter.submitList(items)
}