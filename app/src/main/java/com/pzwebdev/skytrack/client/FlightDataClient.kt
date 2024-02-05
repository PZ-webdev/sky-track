package com.pzwebdev.skytrack.client

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class FlightDataClient {
    private val client = OkHttpClient()

    fun fetchData(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("[API ERROR]", response.toString())
                throw IOException("Unexpected code ${response.code}")
            }

            return response.body?.string() ?: ""
        }
    }
}