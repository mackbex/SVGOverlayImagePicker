package com.picker.overlay.ui.picker.overlay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picker.overlay.R

/**
 * 오버레이 화면 안, SVG 리스트 어뎁터.
 */
class OverlayAdapter : ListAdapter<String, OverlayAdapter.ViewHolder>(
    ItemDiffCallback()
) {
    private var listener: ((path:String, binding: ViewDataBinding) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_overlay_resource,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setPostInterface(listener: ((path: String, binding:ViewDataBinding) -> Unit)?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ViewDataBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(path:String) {
            binding.setVariable(BR.path, path)
            listener?.invoke(path, binding)
            binding.executePendingBindings()
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }
    }
}
