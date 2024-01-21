package com.pzwebdev.skytrack.service

import android.util.Log
import com.pzwebdev.skytrack.client.FlightDataClient
import com.pzwebdev.skytrack.utils.ParsingUtils
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightDataService(private val flightDataViewModel: FlightDataViewModel) {
    private val flightDataClient = FlightDataClient()
        private val openSkyApiUrl =
        "https://airlabs.co/api/v9/flights?api_key=4e870477-5854-4c68-b255-36c50e29df21"

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchDataInBackground() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = flightDataClient.fetchData(openSkyApiUrl)
                val responseData = ParsingUtils.parseResponse(response)
                Log.d("[AIRLAB API]", "Response: ${responseData?.response}")

                responseData?.response?.let { flightDataList ->
                    withContext(Dispatchers.Main) {
                        flightDataViewModel.setFlightDataList(flightDataList)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}