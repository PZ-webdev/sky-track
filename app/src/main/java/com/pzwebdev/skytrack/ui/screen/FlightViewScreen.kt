package com.pzwebdev.skytrack.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.pzwebdev.skytrack.service.FlightDataService
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel
import com.pzwebdev.skytrack.viewModel.LocationViewModel
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@Composable
fun FlightViewScreen(
    navController: NavController,
    flightDataViewModel: FlightDataViewModel,
) {
    val locationViewModel = LocationViewModel()
    val uiSettings: MapUiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val flightDataList by flightDataViewModel.flightDataList.observeAsState(emptyList())
    val latLng = getLatLang(locationViewModel)
    val cameraPositionState = rememberCameraPositionState {
        position = fromLatLngZoom(
            latLng,
            6f
        )
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState
    ) {
        if (flightDataList.isNotEmpty()) {
            flightDataList.forEach { flightData ->
                Marker(
                    position = LatLng(flightData.lat!!, flightData.lng!!),
                    title = flightData.flightNumber!!,
                    onClick = {
                        // Show flight details screen
                        getDetails(
                            flightIata = flightData.flightIata!!,
                            navController = navController,
                            flightDataViewModel = flightDataViewModel
                        )

                        return@Marker false
                    }
                )
            }
        }
    }
}

private fun getLatLang(viewModel: LocationViewModel): LatLng {
    return LatLng(
        viewModel.currentLocalization.value?.latitude ?: 50.0295,
        viewModel.currentLocalization.value?.longitude ?: 22.0067
    )
}

private fun getDetails(
    flightIata: String,
    navController: NavController,
    flightDataViewModel: FlightDataViewModel
) {
    val flightDataService = FlightDataService(flightDataViewModel)
    flightDataService.getFlightDetails(flightIata)

    Log.d("[DETAILS]", "Flight details of Flight: $flightIata")
    navController.navigate("flightDetailsScreen/$flightIata")
}
