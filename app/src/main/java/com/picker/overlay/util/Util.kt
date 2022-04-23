package com.picker.overlay.util

import android.content.Context
import android.util.TypedValue

object Util {
    fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        )
            .toInt()
    }
}