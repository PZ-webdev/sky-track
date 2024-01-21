package com.pzwebdev.skytrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pzwebdev.skytrack.service.FlightDataService
import com.pzwebdev.skytrack.ui.component.AppNavigation
import com.pzwebdev.skytrack.ui.theme.SkyTrackTheme
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel

class MainActivity : ComponentActivity() {
    private val flightDataViewModel = FlightDataViewModel()
    private val flightDataService = FlightDataService(flightDataViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkyTrackTheme {
                AppNavigation(flightDataViewModel)
            }
        }

        flightDataService.fetchDataInBackground()
    }
}
