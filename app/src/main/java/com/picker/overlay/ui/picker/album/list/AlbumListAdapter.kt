package com.picker.overlay.ui.picker.album.list

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
//import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picker.overlay.R
import com.picker.overlay.databinding.ItemAlbumBinding
import com.picker.overlay.databinding.ItemHorizontalThemeCardviewBinding
import com.picker.overlay.domain.model.*



class AlbumListAdapter : ListAdapter<Album, AlbumListAdapter.ViewHolder>(
    ItemDiffCallback()
) {
    private var listener: ((album:Album, binding: ViewDataBinding) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is PhotoAlbum -> R.layout.item_album
            else -> throw Resources.NotFoundException("No matched layout found.")
        }
    }

    fun setPostInterface(listener: ((item:Album,binding:ViewDataBinding) -> Unit)?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ItemAlbumBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Album) {
            binding.setVariable(BR.model, item)
            listener?.invoke(item, binding)

            binding.executePendingBindings()
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(
            oldItem: Album,
            newItem: Album
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Album,
            newItem: Album
        ): Boolean {
            return oldItem.title == newItem.title
        }
    }
}
