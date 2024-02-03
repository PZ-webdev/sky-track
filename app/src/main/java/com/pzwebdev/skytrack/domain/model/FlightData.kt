package com.pzwebdev.skytrack.domain.model

data class FlightData(
    val hex: String?,
    val regNumber: String?,
    val flag: String?,
    val lat: Double?,
    val lng: Double?,
    val alt: Int?,
    val dir: Double?,
    val speed: Int?,
    val vSpeed: Int?,
    val squawk: String?,
    val flightNumber: String?,
    val flightIcao: String?,
    val flightIata: String?,
    val depIcao: String?,
    val depIata: String?,
    val arrIcao: String?,
    val arrIata: String?,
    val airlineIcao: String?,
    val airlineIata: String?,
    val aircraftIcao: String?,
    val updated: Long?,
    val status: String?,
    val type: String?
)

data class ResponseData(
    val request: RequestData?,
    val response: List<FlightData>?
)

data class RequestData(
    val lang: String?,
    val currency: String?,
    val time: Int?,
    val id: String?,
    val server: String?,
    val host: String?,
    val pid: Int?,
    val key: KeyData?,
    val params: ParamsData?,
    val version: Int?,
    val method: String?,
    val client: ClientData?
)

data class KeyData(
    val id: Int?,
    val apiKey: String?,
    val type: String?,
    val expired: String?,
    val registered: String?,
    val upgraded: String?,
    val limitsByHour: Int?,
    val limitsByMinute: Int?,
    val limitsByMonth: Int?,
    val limitsTotal: Int?
)

data class ParamsData(
    val lang: String?
)

data class ClientData(
    val ip: String?,
    val geo: GeoData?,
    val connection: ConnectionData?,
    val device: Any?,
    val agent: Any?,
    val karma: KarmaData?
)

data class GeoData(
    val countryCode: String?,
    val country: String?,
    val continent: String?,
    val city: String?,
    val lat: Double?,
    val lng: Double?,
    val timezone: String?
)

data class ConnectionData(
    val ispCode: Int?,
    val ispName: String?
)

data class KarmaData(
    val isBlocked: Boolean?,
    val isCrawler: Boolean?,
    val isBot: Boolean?,
    val isFriend: Boolean?,
    val isRegular: Boolean?
)
