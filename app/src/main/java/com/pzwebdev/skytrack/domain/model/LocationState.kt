package com.pzwebdev.skytrack.domain.model

data class LocationState(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var accuracy: Float = 0f
)