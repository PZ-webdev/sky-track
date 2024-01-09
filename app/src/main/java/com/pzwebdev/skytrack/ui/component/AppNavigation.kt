package com.pzwebdev.skytrack.ui.component

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pzwebdev.skytrack.ui.screen.FlightViewScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "flightViewScreen") {
        composable("flightViewScreen") { FlightViewScreen(navController) }
//        composable("mapScreen") { MapScreen(navController) }
    }
}