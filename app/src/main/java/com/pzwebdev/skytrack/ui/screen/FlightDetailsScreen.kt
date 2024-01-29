package com.pzwebdev.skytrack.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pzwebdev.skytrack.R
import com.pzwebdev.skytrack.utils.FlightDataDetails
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel

@Composable
fun FlightDetailsScreen(
    navController: NavController,
    flightDataViewModel: FlightDataViewModel,
    flightIata: String?
) {
    val flightData: FlightDataDetails? = flightDataViewModel.flightDataDetails.value

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = null)
                }
                flightData?.let {
                    Text(
                        text = it.flightIcao,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_plane),
                contentDescription = "Plane Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.height(16.dp))

            flightData?.let {
                FlightInfoRow("Aircraft Name", it.model)
                FlightInfoRow("Registration", it.regNumber)
                FlightInfoRow("Flight Number", "${it.flightIcao} (${it.flightIata})")
                FlightInfoRow("Airline", "${it.airlineIcao} (${it.airlineIata})")
                FlightInfoRow("Departure", it.depTime)
                FlightInfoRow("Arrival", it.arrTime)
            }
        }
    }
}

@Composable
fun FlightInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, modifier = Modifier.weight(1f))
        Text(value, modifier = Modifier.weight(1f))
    }
}