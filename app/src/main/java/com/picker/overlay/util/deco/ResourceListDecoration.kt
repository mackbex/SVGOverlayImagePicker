package com.picker.overlay.util.deco

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.picker.overlay.R
import com.picker.overlay.util.Util


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

//        // spanIndex = 0 -> 왼쪽
//        // spanIndex = 1 -> 오른쪽
//        val lp = view.layoutParams as GridLayoutManager.LayoutParams
//        val spanIndex = lp.spanIndex
//        if (spanIndex == 0) {
//            //왼쪽 아이템
//            outRect.left = size10
//            outRect.right = size5
//        } else if (spanIndex == 1) {
//            //오른쪽 아이템
//            outRect.left = size5
//            outRect.right = size10
//        }
//        outRect.top = size10
//        outRect.right = size10
//        outRect.bottom = size10
//        outRect.left = size10
    }
}
