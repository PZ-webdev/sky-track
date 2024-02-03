package com.pzwebdev.skytrack.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pzwebdev.skytrack.model.LocationState

class LocationViewModel : ViewModel() {
    private val _currentLocation = MutableLiveData<LocationState>()

    val currentLocalization: MutableLiveData<LocationState>
        get() = _currentLocation

    fun setCurrentLocation(locationState: LocationState) {
        Log.d("LocationViewModel", "setCurrentLocation: $locationState")
        _currentLocation.value = locationState
    }
}