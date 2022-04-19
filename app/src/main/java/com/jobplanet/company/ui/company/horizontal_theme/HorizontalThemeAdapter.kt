package com.jobplanet.company.ui.company.horizontal_theme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jobplanet.company.R
import com.jobplanet.company.databinding.ItemHorizontalThemeCardviewBinding
import com.jobplanet.company.domain.model.*


/**
 * SearchResult 뷰의 Horizontal cardview용 adapter
 */

class HorizontalThemeAdapter : ListAdapter<Theme, HorizontalThemeAdapter.ViewHolder>(
    ItemDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_horizontal_theme_cardview,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHorizontalThemeCardviewBinding):
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                Snackbar.make(view, binding.model?.title ?: run { "" }, Snackbar.LENGTH_SHORT).show()
            }
        }
        fun bind(item:Theme) {
            binding.setVariable(BR.model, item)
            binding.executePendingBindings()
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Theme>() {

        override fun areItemsTheSame(
            oldItem: Theme,
            newItem: Theme
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Theme,
            newItem: Theme
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
