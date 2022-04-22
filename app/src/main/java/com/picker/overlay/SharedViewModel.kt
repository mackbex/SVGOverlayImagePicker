package com.picker.overlay

import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
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


    fun checkStorageAccessPermission(fragment:Fragment, permissionList: Array<String>, callbackGranted:(() -> Unit)? = null, callbackDenied:(() -> Unit)? = null) {
        val requestStoragePermission = fragment.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val requiredList = mutableListOf<String>()
            permissions.entries.forEach {
                if(!it.value) requiredList.add(it.key)
            }

            if(requiredList.size <= 0) {
                callbackGranted?.invoke()
            }
            else {
                callbackDenied?.invoke()
            }
        }

        requestStoragePermission.launch(permissionList)
    }
}