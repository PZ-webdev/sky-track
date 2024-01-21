package com.pzwebdev.skytrack.ui.component

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pzwebdev.skytrack.ui.screen.FlightViewScreen
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel

@Composable
fun AppNavigation(flightDataViewModel: FlightDataViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "flightViewScreen") {
        composable("flightViewScreen") { FlightViewScreen(navController, flightDataViewModel) }
//        composable("mapScreen") { MapScreen(navController) }
    }
}