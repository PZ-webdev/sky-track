package com.pzwebdev.skytrack.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pzwebdev.skytrack.utils.FlightData

class FlightDataViewModel : ViewModel() {
    private val _flightDataList = MutableLiveData<List<FlightData>>()
    val flightDataList: LiveData<List<FlightData>>
        get() = _flightDataList

    fun setFlightDataList(dataList: List<FlightData>) {
        Log.d("FlightDataViewModel", "setFlightDataList: $dataList")
        _flightDataList.value = dataList
    }

}