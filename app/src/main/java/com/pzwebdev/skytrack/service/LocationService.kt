package com.pzwebdev.skytrack.service

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.pzwebdev.skytrack.domain.model.LocationState
import com.pzwebdev.skytrack.viewModel.LocationViewModel

class LocationService : Service() {
    inner class LocalBinder : Binder() {
        fun getService(): LocationService = this@LocationService
    }

    private val binder = LocalBinder()

    private val locationViewModel = LocationViewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationCallback: LocationCallback? = null
    val locationState = LocationState()

    override fun onCreate() {
        Log.d("[LOCALIZATION]", "Calling method -> onCreate")
        super.onCreate()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        requestLocationUpdates()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    private fun requestLocationUpdates() {
        Log.d("[LOCALIZATION]", "Calling method -> requestLocationUpdates")

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let {
                    locationState.latitude = it.latitude
                    locationState.longitude = it.longitude
                    locationState.accuracy = it.accuracy
                }

                Log.d("[LOCALIZATION]", locationState.toString())
                locationViewModel.setCurrentLocation(locationState)
            }
        }

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000 // 1 sekunda
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Handle lack of permissions
            Log.w("[LOCALIZATION]", "Handle lack of permissions")
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }
}

