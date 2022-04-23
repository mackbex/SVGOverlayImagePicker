package com.picker.overlay.util.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.R
import com.picker.overlay.util.wrapper.Resource
import com.picker.overlay.ui.picker.overlay.OverlayAdapter


/**
 * Recyclerview databinding Ext
 */

@BindingAdapter(value = ["bind:items", "bind:listAdapter"])
fun<ITEM,HOLDER : RecyclerView.ViewHolder?> bindItems(recyclerView: RecyclerView,
                                                      items: Resource<List<ITEM>>?,
                                                      listAdapter:androidx.recyclerview.widget.ListAdapter<ITEM,HOLDER>) {
    when(items) {
        is Resource.Success -> {
            listAdapter.submitList(items.data)
        }
        is Resource.Failure -> {
            Snackbar.make(recyclerView.rootView, recyclerView.context.getString(R.string.err_failed_load_data), Snackbar.LENGTH_SHORT).show()
        }
    }
}

/**
 * 리소스 리스트 adapter 아이템.
 */

@BindingAdapter("resourceItems")
fun bindResourceItems(recyclerView: RecyclerView, items: Resource<List<String>>?) {
    val adapter = recyclerView.adapter as OverlayAdapter

    when(items) {
        is Resource.Success -> {
            adapter.submitList(items.data)
        }
        is Resource.Failure -> {
            Snackbar.make(recyclerView.rootView, recyclerView.context.getString(R.string.err_failed_load_data), Snackbar.LENGTH_SHORT).show()
        }
    }
}
