package com.picker.overlay.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

object RequiredPermissions {
    val STORAGE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}

fun dpToPx(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
    )
        .toInt()
}

fun getProgressbar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
}

fun isPermissionGranted(context: Context, permissionList:Array<String>):Boolean {
    var result: Int
    val listPermissionsNeeded: MutableList<String> = ArrayList()

    for (p in permissionList) {
        result = ContextCompat.checkSelfPermission(context, p)
        if (result != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(p)
        }
    }

    return listPermissionsNeeded.isEmpty()
}