package com.pzwebdev.skytrack.utils

import org.json.JSONArray
import org.json.JSONObject

object ParsingUtils {

    fun parseResponse(response: String): ResponseData? {
        return try {
            val jsonObject = JSONObject(response)
            parseResponseData(jsonObject)
        } catch (e: Exception) {
            null
        }
    }

    fun parseResponseData(jsonObject: JSONObject): ResponseData? {
        val requestData = parseRequestData(jsonObject.getJSONObject("request"))
        val responseData = parseFlightDataList(jsonObject.getJSONArray("response"))

        return ResponseData(requestData, responseData)
    }

    fun parseRequestData(requestObject: JSONObject): RequestData {
        return RequestData(
            requestObject.optString("lang"),
            requestObject.optString("currency"),
            requestObject.optInt("time"),
            requestObject.optString("id"),
            requestObject.optString("server"),
            requestObject.optString("host"),
            requestObject.optInt("pid"),
            parseKeyData(requestObject.getJSONObject("key")),
            parseParamsData(requestObject.getJSONObject("params")),
            requestObject.optInt("version"),
            requestObject.optString("method"),
            parseClientData(requestObject.getJSONObject("client"))
        )
    }

    fun parseFlightDataList(responseArray: JSONArray): List<FlightData> {
        val flightDataList = mutableListOf<FlightData>()
        for (i in 0 until responseArray.length()) {
            val flightDataObject = responseArray.getJSONObject(i)
            val flightData = parseFlightData(flightDataObject)
            flightDataList.add(flightData)
        }
        return flightDataList
    }

    fun parseFlightData(flightDataObject: JSONObject): FlightData {
        return FlightData(
            flightDataObject.optString("hex"),
            flightDataObject.optString("reg_number"),
            flightDataObject.optString("flag"),
            flightDataObject.optDouble("lat"),
            flightDataObject.optDouble("lng"),
            flightDataObject.optInt("alt"),
            flightDataObject.optDouble("dir"),
            flightDataObject.optInt("speed"),
            flightDataObject.optInt("v_speed"),
            flightDataObject.optString("squawk"),
            flightDataObject.optString("flight_number"),
            flightDataObject.optString("flight_icao"),
            flightDataObject.optString("flight_iata"),
            flightDataObject.optString("dep_icao"),
            flightDataObject.optString("dep_iata"),
            flightDataObject.optString("arr_icao"),
            flightDataObject.optString("arr_iata"),
            flightDataObject.optString("airline_icao"),
            flightDataObject.optString("airline_iata"),
            flightDataObject.optString("aircraft_icao"),
            flightDataObject.optLong("updated"),
            flightDataObject.optString("status"),
            flightDataObject.optString("type")
        )
    }

    fun parseKeyData(keyObject: JSONObject): KeyData {
        return KeyData(
            keyObject.optInt("id"),
            keyObject.optString("api_key"),
            keyObject.optString("type"),
            keyObject.optString("expired"),
            keyObject.optString("registered"),
            keyObject.optString("upgraded"),
            keyObject.optInt("limits_by_hour"),
            keyObject.optInt("limits_by_minute"),
            keyObject.optInt("limits_by_month"),
            keyObject.optInt("limits_total")
        )
    }

    fun parseParamsData(paramsObject: JSONObject): ParamsData {
        return ParamsData(
            paramsObject.optString("lang")
        )
    }

    fun parseClientData(clientObject: JSONObject): ClientData {
        return ClientData(
            clientObject.optString("ip"),
            parseGeoData(clientObject.getJSONObject("geo")),
            parseConnectionData(clientObject.getJSONObject("connection")),
            clientObject.opt("device"),  // Dostosuj do rzeczywistych danych
            clientObject.opt("agent"),   // Dostosuj do rzeczywistych danych
            parseKarmaData(clientObject.getJSONObject("karma"))
        )
    }

    fun parseGeoData(geoObject: JSONObject): GeoData {
        return GeoData(
            geoObject.optString("countryCode"),
            geoObject.optString("country"),
            geoObject.optString("continent"),
            geoObject.optString("city"),
            geoObject.optDouble("lat"),
            geoObject.optDouble("lng"),
            geoObject.optString("timezone")
        )
    }

    fun parseConnectionData(connectionObject: JSONObject): ConnectionData {
        return ConnectionData(
            connectionObject.optInt("isp_code"),
            connectionObject.optString("isp_name")
        )
    }

    fun parseKarmaData(karmaObject: JSONObject): KarmaData {
        return KarmaData(
            karmaObject.optBoolean("is_blocked"),
            karmaObject.optBoolean("is_crawler"),
            karmaObject.optBoolean("is_bot"),
            karmaObject.optBoolean("is_friend"),
            karmaObject.optBoolean("is_regular")
        )
    }

    fun parseFlightDetails(response: String): FlightDataDetails? {
        return try {
            val jsonObject = JSONObject(response)
                val responseData = parseFlightDataList(jsonObject.getJSONArray("response"))

            parseResponseFlightDetails(responseData)
        } catch (e: Exception) {
            null
        }
    }

    private fun parseResponseFlightDetails(jsonObject: JSONObject): FlightDataDetails? {
        return FlightDataDetails(
            hex = jsonObject.optString("hex", ""),
            regNumber = jsonObject.optString("reg_number", ""),
            aircraftIcao = jsonObject.optString("aircraft_icao", ""),
            flag = jsonObject.optString("flag", ""),
            lat = jsonObject.optDouble("lat", 0.0),
            lng = jsonObject.optDouble("lng", 0.0),
            alt = jsonObject.optInt("alt", 0),
            dir = jsonObject.optInt("dir", 0),
            speed = jsonObject.optInt("speed", 0),
            vSpeed = jsonObject.optInt("v_speed", 0),
            squawk = jsonObject.optString("squawk", ""),
            airlineIcao = jsonObject.optString("airline_icao", ""),
            airlineIata = jsonObject.optString("airline_iata", ""),
            flightNumber = jsonObject.optString("flight_number", ""),
            flightIcao = jsonObject.optString("flight_icao", ""),
            flightIata = jsonObject.optString("flight_iata", ""),
            csAirlineIata = jsonObject.optString("cs_airline_iata", null),
            csFlightNumber = jsonObject.optString("cs_flight_number", null),
            csFlightIata = jsonObject.optString("cs_flight_iata", null),
            depIcao = jsonObject.optString("dep_icao", ""),
            depIata = jsonObject.optString("dep_iata", ""),
            depTerminal = jsonObject.optString("dep_terminal", null),
            depGate = jsonObject.optString("dep_gate", ""),
            depTime = jsonObject.optString("dep_time", ""),
            depTimeTs = jsonObject.optLong("dep_time_ts", 0),
            depTimeUtc = jsonObject.optString("dep_time_utc", ""),
            arrIcao = jsonObject.optString("arr_icao", ""),
            arrIata = jsonObject.optString("arr_iata", ""),
            arrTerminal = jsonObject.optString("arr_terminal", null),
            arrGate = jsonObject.optString("arr_gate", ""),
            arrBaggage = jsonObject.optString("arr_baggage", ""),
            arrTime = jsonObject.optString("arr_time", ""),
            arrTimeTs = jsonObject.optLong("arr_time_ts", 0),
            arrTimeUtc = jsonObject.optString("arr_time_utc", ""),
            duration = jsonObject.optInt("duration", 0),
            delayed = jsonObject.optBoolean("delayed"),
            depDelayed = jsonObject.optBoolean("dep_delayed"),
            arrDelayed = jsonObject.optBoolean("arr_delayed"),
            updated = jsonObject.optLong("updated", 0),
            status = jsonObject.optString("status", ""),
            age = jsonObject.optInt("age", 0),
            built = jsonObject.optInt("built", 0),
            engine = jsonObject.optString("engine", ""),
            engineCount = jsonObject.optString("engine_count", ""),
            model = jsonObject.optString("model", ""),
            manufacturer = jsonObject.optString("manufacturer", ""),
            msn = jsonObject.optString("msn", ""),
            type = jsonObject.optString("type", "")
        )
    }
}
