package com.jobplanet.company.ui.company.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jobplanet.company.R
import com.jobplanet.company.databinding.ItemCompanyBinding
import com.jobplanet.company.domain.model.*
import java.lang.IllegalStateException

/**
 * SearchResult 뷰 adapter
 */
class SearchResultAdapter : ListAdapter<Items, SearchResultAdapter.ViewHolder>(ItemDiffCallback()) {
    private var listener: ((item:Items, binding:ViewDataBinding) -> Unit)? = null

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

    /**
     * 모델마다 전용 layout_id가 기본값으로 있기때문에, list_item을 따로 설정해줄 필요 없음. (viewType에 id 직접할당함.)
     */
    override fun getItemViewType(position: Int): Int {
        return currentList[position].layout_id
    }

    /**
     * 바인딩 후, 추가작업이 있을 경우 Caller Fragment에서 설정함.
     */
    fun setPostInterface(listener: ((item:Items,binding:ViewDataBinding) -> Unit)?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ViewDataBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item:Items) {
            binding.setVariable(BR.model, item)
            listener?.invoke(item, binding)

            binding.executePendingBindings()
        }
    }

    /**
     * Note :
     * 인터페이스로 상속받기 때문에, 삭제, 추가 기능이 요청 될 시 정상작동하는지 확인해야함.
     */
    private class ItemDiffCallback : DiffUtil.ItemCallback<Items>() {

        override fun areItemsTheSame(
            oldItem: Items,
            newItem: Items
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Items,
            newItem: Items
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
}

