package com.saibabui.network.utils

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException

class TokenAuthenticatorWithRefresh : Authenticator {
    private val TAG = "TokenAuthenticatorWithRefresh"
    
    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d(TAG, "Authenticating request for ${response.request.url}")
        
        // If we're not authorized (401), try to refresh the token
        if (response.code == 401) {
            Log.d(TAG, "Received 401 - attempting token refresh")
            
            // Try to refresh the token
            val newToken = refreshAccessToken()
            
            if (!newToken.isNullOrEmpty()) {
                Log.d(TAG, "Successfully refreshed token, retrying request")
                // If we got a new token, retry the request with the new token
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                    .build()
            } else {
                Log.d(TAG, "Failed to refresh token")
                // If we couldn't refresh, clear tokens and potentially redirect to login
                TokenManager.clearTokens()
            }
        }
        
        return null // No retry needed or possible
    }
    
    @Synchronized
    private fun refreshAccessToken(): String? {
        // Get the stored refresh token
        val refreshToken = TokenManager.getRefreshToken()
        
        if (refreshToken.isNullOrEmpty()) {
            Log.e(TAG, "No refresh token available")
            return null
        }
        
        // Create a synchronous request to refresh the token
        try {
            // Use a new OkHttpClient instance to make the refresh call
            val client = OkHttpClient()
            
            val body = "application/json; charset=utf-8".toMediaType()
            val jsonBody = """{"refreshToken": "$refreshToken"}"""
            val request = Request.Builder()
                .url("https://3bb5bd740e05.ngrok-free.app/auth/refresh") // Using the same base URL
                .post(RequestBody.create(body, jsonBody))
                .build()
            
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    // Parse the response to get the new access token
                    val responseBody = response.body?.string()
                    Log.d(TAG, "Refresh response: $responseBody")

                    // Here we would parse the JSON response properly
                    // For now, using a simple approach to extract the token
                    // In a real app, you should use Moshi, Gson, or Kotlin serialization
                    val newAccessToken = extractTokenFromResponse(responseBody)
                    val newRefreshToken = extractRefreshTokenFromResponse(responseBody)

                    if (!newAccessToken.isNullOrEmpty()) {
                        // Save the new tokens
                        if (!newRefreshToken.isNullOrEmpty()) {
                            TokenManager.saveTokens(newAccessToken, newRefreshToken)
                        } else {
                            TokenManager.saveAccessToken(newAccessToken)
                        }
                        return newAccessToken
                    } else {
                    }
                } else {
                    Log.e(TAG, "Token refresh failed with code: ${response.code}")
                }

            }
        } catch (e: IOException) {
            Log.e(TAG, "Error during token refresh: ${e.message}", e)
        }
        
        return null
    }
    
    private fun extractTokenFromResponse(responseBody: String?): String? {
        // Looking for a pattern like "accessToken":"..."
        val regex = Regex("\"accessToken\"\\s*:\\s*\"([^\"]+)\"")
        val match = regex.find(responseBody ?: "")
        return match?.groupValues?.getOrNull(1)
    }
    
    private fun extractRefreshTokenFromResponse(responseBody: String?): String? {
        // Looking for a pattern like "refreshToken":"..."
        val regex = Regex("\"refreshToken\"\\s*:\\s*\"([^\"]+)\"")
        val match = regex.find(responseBody ?: "")
        return match?.groupValues?.getOrNull(1)
    }
}