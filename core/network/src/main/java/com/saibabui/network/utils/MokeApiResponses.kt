package com.saibabui.network.utils

import android.content.Context
import java.io.IOException

fun getMockDataFromUrl(url: String,context: Context): String {
    val assetName = when {
        url.endsWith("/generate-otp") -> "auth_user_login_response.json"
        url.endsWith("/verify-otp") -> "auth_verify_otp_response.json"
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