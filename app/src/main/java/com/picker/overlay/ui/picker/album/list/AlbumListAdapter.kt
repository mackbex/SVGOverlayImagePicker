package com.picker.overlay.ui.picker.album.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.picker.overlay.R
import com.picker.overlay.domain.model.*


/**
 * 앨범 리스트 어뎁터.
 */
class AlbumListAdapter : ListAdapter<Album, AlbumListAdapter.ViewHolder>(
    ItemDiffCallback()
) {
    private var listener: ((album:Album, binding: ViewDataBinding) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_album,
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
    * 버튼 클릭 등의 이벤트 처리를 부모 fragment에서 가시적으로 선언 및 처리 하기 위한 리스너 생성.
     */
    fun setPostInterface(listener: ((item:Album,binding:ViewDataBinding) -> Unit)?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ViewDataBinding):
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
            return oldItem.list == newItem.list
        }
    }
}
