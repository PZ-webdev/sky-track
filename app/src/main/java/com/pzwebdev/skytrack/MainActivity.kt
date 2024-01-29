package com.pzwebdev.skytrack

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pzwebdev.skytrack.common.PermissionManager
import com.pzwebdev.skytrack.common.PermissionManagerResultListener
import com.pzwebdev.skytrack.service.FlightDataService
import com.pzwebdev.skytrack.ui.component.AppNavigation
import com.pzwebdev.skytrack.ui.theme.SkyTrackTheme
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel

class MainActivity : ComponentActivity(), PermissionManagerResultListener {
    private val permissionManager = PermissionManager(this, this)
    private val flightDataViewModel = FlightDataViewModel()
    private val flightDataService = FlightDataService(flightDataViewModel)

    private val handler = Handler(Looper.getMainLooper())
    private val fetchDataRunnable = object : Runnable {
        override fun run() {
            flightDataService.fetchDataInBackground()
            handler.postDelayed(this, 5000) // Starts every 5 seconds
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkyTrackTheme {
                AppNavigation(flightDataViewModel)
            }
        }

        // Running fetchDataInBackground for the first time
        flightDataService.fetchDataInBackground()

        // Run fetchDataInBackground every 5 seconds
        handler.postDelayed(fetchDataRunnable, 5000)
    }

    override fun onDestroy() {
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
