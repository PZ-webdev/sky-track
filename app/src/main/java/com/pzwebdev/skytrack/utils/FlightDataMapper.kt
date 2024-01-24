package com.pzwebdev.skytrack.utils

import org.json.JSONArray
import org.json.JSONObject

object FlightDataMapper {
    fun parseResponse(response: String): ResponseData {
        val jsonObject = JSONObject(response)
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
}