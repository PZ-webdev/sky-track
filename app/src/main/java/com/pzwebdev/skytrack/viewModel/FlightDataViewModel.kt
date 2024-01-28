package com.pzwebdev.skytrack.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pzwebdev.skytrack.utils.FlightData
import com.pzwebdev.skytrack.utils.FlightDataDetails

class FlightDataViewModel : ViewModel() {
    private val _flightDataList = MutableLiveData<List<FlightData>>()
    private val _flightDataDetails = MutableLiveData<FlightDataDetails>()

    val flightDataList: LiveData<List<FlightData>>
        get() = _flightDataList

    val flightDataDetails: MutableLiveData<FlightDataDetails>
        get() = _flightDataDetails

    fun setFlightDataList(dataList: List<FlightData>) {
        Log.d("FlightDataViewModel", "setFlightDataList: $dataList")
        _flightDataList.value = dataList
    }

    fun setFlightDataDetails(data: FlightDataDetails) {
      Log.d("FlightDataViewModel", "setFlightData: $data")
        _flightDataDetails.value = data
    }

}