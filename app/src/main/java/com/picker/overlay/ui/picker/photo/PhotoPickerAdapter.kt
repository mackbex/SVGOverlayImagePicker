package com.picker.overlay.ui.picker.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picker.overlay.R
import com.picker.overlay.domain.model.AlbumItem
import com.picker.overlay.domain.model.Photo

/**
 * PhotoPicker 어뎁터.
 */
class PhotoPickerAdapter : ListAdapter<AlbumItem, PhotoPickerAdapter.ViewHolder>(
    ItemDiffCallback()
) {
    private var listener: ((item:AlbumItem, binding: ViewDataBinding) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_photo,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setPostInterface(listener: ((item: AlbumItem, binding:ViewDataBinding) -> Unit)?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ViewDataBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item:AlbumItem) {
            binding.setVariable(BR.model, item)
            listener?.invoke(item, binding)
            binding.executePendingBindings()
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<AlbumItem>() {
        override fun areItemsTheSame(
            oldItem: AlbumItem,
            newItem: AlbumItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AlbumItem,
            newItem: AlbumItem
        ): Boolean {
            return oldItem.uri == newItem.uri
        }
    }
}
