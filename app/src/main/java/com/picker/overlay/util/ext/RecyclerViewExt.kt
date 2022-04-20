package com.picker.overlay.util.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.R
import com.picker.overlay.domain.model.Album
import com.picker.overlay.util.Resource
import com.picker.overlay.domain.model.Theme
import com.picker.overlay.ui.company.horizontal_theme.HorizontalThemeAdapter
import com.picker.overlay.ui.picker.album.list.AlbumListAdapter


/**
 * Recyclerview databinding Ext
 */

@BindingAdapter("items")
fun bindItems(recyclerView: RecyclerView, items: Resource<List<Album>>?) {
    val adapter = recyclerView.adapter as AlbumListAdapter
    when(items) {
        is Resource.Success -> {
            adapter.submitList(items.data)
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