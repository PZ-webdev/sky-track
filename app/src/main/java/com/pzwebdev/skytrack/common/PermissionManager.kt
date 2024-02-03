package com.pzwebdev.skytrack.common

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

interface PermissionManagerResultListener {
    fun onPermissionResult(result: Map<String, Boolean>)
}

class PermissionManager(
    private val context: Context,
    private val listener: PermissionManagerResultListener
) : DefaultLifecycleObserver {
    private var launcher: ActivityResultLauncher<Array<String>>
    private val permissionsToRequest: List<String> = listOf(
        android.Manifest.permission.INTERNET,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    )

    init {
        if (context is ComponentActivity) {
            context.lifecycle.addObserver(this)
            with(context) {
                launcher = this.registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { listener.onPermissionResult(it) }
            }
        } else {
            throw IllegalArgumentException("Context must be a lifecycle owner")
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        getPermissionsToGrant().let {
            if (it.isNotEmpty()) {
                launcher.launch(it)
            }
        }
    }

    private fun getPermissionsToGrant(): Array<String> =
        permissionsToRequest.filter { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
}