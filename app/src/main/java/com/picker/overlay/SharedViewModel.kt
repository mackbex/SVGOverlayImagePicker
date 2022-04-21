package com.picker.overlay

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(

):ViewModel() {

    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    var permissionGranted = false

    fun checkPermissions(activity:Activity):Boolean {
        val requiredPermissions = REQUIRED_PERMISSIONS.toMutableList()
        for (permission in REQUIRED_PERMISSIONS) {
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    requiredPermissions.remove(permission)
                }
            }
        }
        permissionGranted = requiredPermissions.size <= 0
        return permissionGranted
    }

    fun requestPermission(activity:Activity) {
        val requiredPermissions = REQUIRED_PERMISSIONS.toMutableList()
        if (requiredPermissions.size > 0) {
            ActivityCompat.requestPermissions(
                activity,
                requiredPermissions.toTypedArray(),
                REQUEST_CODE_PERMISSIONS
            )
        } else {
            permissionGranted = true
        }
    }
}