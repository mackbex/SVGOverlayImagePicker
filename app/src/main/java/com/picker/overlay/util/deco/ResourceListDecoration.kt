package com.picker.overlay.util.deco

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.picker.overlay.R

/**
 * SVG 그리드 recycler뷰용 데코 클래스
 */
class ResourceListDecoration(private val context: Context) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        if (position == 0) {
            outRect.left = context.resources.getDimension(R.dimen.list_overlay_resource_edge_margin).toInt()
            outRect.right = context.resources.getDimension(R.dimen.list_overlay_resource_inner_margin).toInt()
        }
        else if(position == itemCount -1) {
            outRect.left = context.resources.getDimension(R.dimen.list_overlay_resource_inner_margin).toInt()
            outRect.right = context.resources.getDimension(R.dimen.list_overlay_resource_edge_margin).toInt()
        }
        else {
            outRect.left = context.resources.getDimension(R.dimen.list_overlay_resource_inner_margin).toInt()
            outRect.right = context.resources.getDimension(R.dimen.list_overlay_resource_inner_margin).toInt()
        }
    }
}
