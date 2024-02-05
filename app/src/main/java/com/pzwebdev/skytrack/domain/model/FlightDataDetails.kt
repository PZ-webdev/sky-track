package com.pzwebdev.skytrack.domain.model

data class FlightDataDetails(
    val hex: String,
    val regNumber: String,
    val aircraftIcao: String,
    val flag: String,
    val lat: Double,
    val lng: Double,
    val alt: Int,
    val dir: Int,
    val speed: Int,
    val vSpeed: Int,
    val squawk: String,
    val airlineIcao: String,
    val airlineIata: String,
    val flightNumber: String,
    val flightIcao: String,
    val flightIata: String,
    val csAirlineIata: String?,
    val csFlightNumber: String?,
    val csFlightIata: String?,
    val depIcao: String,
    val depIata: String,
    val depTerminal: String?,
    val depGate: String,
    val depTime: String,
    val depTimeTs: Long,
    val depTimeUtc: String,
    val arrIcao: String,
    val arrIata: String,
    val arrTerminal: String?,
    val arrGate: String,
    val arrBaggage: String,
    val arrTime: String,
    val arrTimeTs: Long,
    val arrTimeUtc: String,
    val duration: Int,
    val delayed: Boolean?,
    val depDelayed: Boolean?,
    val arrDelayed: Boolean?,
    val updated: Long,
    val status: String,
    val age: Int,
    val built: Int,
    val engine: String,
    val engineCount: String,
    val model: String,
    val manufacturer: String,
    val msn: String,
    val type: String
)