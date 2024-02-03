package com.pzwebdev.skytrack

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.pzwebdev.skytrack.common.PermissionManager
import com.pzwebdev.skytrack.common.PermissionManagerResultListener
import com.pzwebdev.skytrack.domain.model.LocationState
import com.pzwebdev.skytrack.service.FlightDataService
import com.pzwebdev.skytrack.service.LocationService
import com.pzwebdev.skytrack.ui.component.AppNavigation
import com.pzwebdev.skytrack.ui.theme.SkyTrackTheme
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel

class MainActivity : ComponentActivity(), PermissionManagerResultListener {
    private val permissionManager = PermissionManager(this, this)
    private val flightDataViewModel = FlightDataViewModel()
    private val flightDataService = FlightDataService(flightDataViewModel)
    private var locationService: LocationService? = null
    private var serviceBound = false

    private val handler = Handler(Looper.getMainLooper())
    private val fetchDataRunnable = object : Runnable {
        override fun run() {
            flightDataService.fetchDataInBackground()
            handler.postDelayed(this, 5000) // Starts every 5 seconds
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.LocalBinder
            locationService = binder.getService()
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            locationService = null
            serviceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkyTrackTheme {
                AppNavigation(flightDataViewModel)
            }
        }

        val serviceIntent = Intent(this, LocationService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        // Running fetchDataInBackground for the first time
        flightDataService.fetchDataInBackground()

        // Run fetchDataInBackground every 5 seconds
        handler.postDelayed(fetchDataRunnable, 5000)
    }

    override fun onDestroy() {
        if (serviceBound) {
            unbindService(serviceConnection)
            serviceBound = false
        }

        super.onDestroy()
        // Stop execution of fetchDataInBackground when activity is destroyed
        handler.removeCallbacks(fetchDataRunnable)
    }

    override fun onPermissionResult(result: Map<String, Boolean>) {
        if (result.values.contains(false)) {
            Log.w("[PERMISSION]", "Permission not granted: $result")
        } else {
            Log.d("[PERMISSION]", "Permission granted: $result")
        }
    }
}
