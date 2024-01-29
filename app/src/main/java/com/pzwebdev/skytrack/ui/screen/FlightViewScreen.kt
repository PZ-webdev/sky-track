package com.pzwebdev.skytrack.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.pzwebdev.skytrack.service.FlightDataService
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel

@Composable
fun FlightViewScreen(
    navController: NavController,
    flightDataViewModel: FlightDataViewModel,
) {
    val uiSettings: MapUiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(50.0295, 22.0067),
            6f
        ) // TODO: get current position
    }

    val flightDataList by flightDataViewModel.flightDataList.observeAsState(emptyList())

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

fun getDetails(
    flightIata: String,
    navController: NavController,
    flightDataViewModel: FlightDataViewModel
) {
    val flightDataService = FlightDataService(flightDataViewModel)

    flightDataService.getFlightDetails(flightIata)

    Log.d("[DETAILS]", "Flight details of Flight: $flightIata")
    navController.navigate("flightDetailsScreen/$flightIata")
}
