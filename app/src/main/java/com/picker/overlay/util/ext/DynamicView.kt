package com.picker.overlay.util.ext

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

class DynamicView {

    fun getProgressbar(context: Context):CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = 5f
            centerRadius = 30f
        }
    }
}