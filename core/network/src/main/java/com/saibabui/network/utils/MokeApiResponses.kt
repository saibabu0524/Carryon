package com.saibabui.network.utils

import android.content.Context
import java.io.IOException

fun getMockDataFromUrl(url: String,context: Context): String {
    val assetName = when {
        url.contains("login") -> "login_response.json"
        url.contains("signup") -> "login_response.json"
        else -> "default.json"
    }
    return loadJsonFromAssets(assetName,context)
}

private fun loadJsonFromAssets(fileName: String,context: Context): String {
    return try {
        context.assets.open(fileName).use { inputStream ->
            inputStream.bufferedReader().readText()
        }
    } catch (e: IOException) {
        "{ \"message\": \"Mock data not found for $fileName\" }"
    }
}