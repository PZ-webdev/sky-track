package com.pzwebdev.skytrack.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pzwebdev.skytrack.ui.component.GoogleMapView

@Composable
fun FlightViewScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleMapView()
    }
}

@Composable
@Preview
fun FlightViewScreenPreview() {
    val navController = rememberNavController()
    FlightViewScreen(navController = navController)
}