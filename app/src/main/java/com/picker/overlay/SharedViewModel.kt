package com.picker.overlay

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(

):ViewModel() {

    val REQUIRED_STORAGE_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)



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
}