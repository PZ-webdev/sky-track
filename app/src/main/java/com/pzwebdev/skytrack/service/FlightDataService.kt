package com.pzwebdev.skytrack.service

import android.util.Log
import com.pzwebdev.skytrack.client.FlightDataClient
import com.pzwebdev.skytrack.common.const.Constants
import com.pzwebdev.skytrack.domain.parser.ParsingUtils
import com.pzwebdev.skytrack.viewModel.FlightDataViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightDataService(private val flightDataViewModel: FlightDataViewModel) {
    private val flightDataClient = FlightDataClient()
    private val openSkyApiUrl = Constants.OPEN_SKY_API_URL
    private val ZOOM_VALUE = Constants.ZOOM_VAL
    private val API_KEY = Constants.API_KEY

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchDataInBackground() {
        val URL = "$openSkyApiUrl/flights?zoom=$ZOOM_VALUE&api_key=$API_KEY"
        Log.i("[AIRLAB API]", "URL: $URL")

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = flightDataClient.fetchData(URL)
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

    @OptIn(DelicateCoroutinesApi::class)
    fun getFlightDetails(flightIata: String) {
        val URL = "$openSkyApiUrl/flight?flight_iata=$flightIata&api_key=$API_KEY"
        Log.i("[AIRLAB API]", "URL: $URL")

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = flightDataClient.fetchData(URL)
                val responseData = ParsingUtils.parseFlightDetails(response)
                Log.d("[AIRLAB API]", "Response Details: ${responseData}")

                responseData?.let { flightData ->
                    withContext(Dispatchers.Main) {
                        flightDataViewModel.setFlightDataDetails(flightData)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}