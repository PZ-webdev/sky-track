package com.pzwebdev.skytrack.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GoogleMapView() {
    val uiSettings: MapUiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.NORMAL)) }
    val location = LatLng(50.0295, 22.0067) // Rzeszów główny
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(location, 6f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth(),
        properties = properties,
        uiSettings = uiSettings,
        cameraPositionState = cameraPositionState
    )
}