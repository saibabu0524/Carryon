package com.saibabui.network.utils

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREFS_NAME = "auth_prefs"
    private const val TOKEN_KEY = "access_token"
    private const val REFRESH_TOKEN_KEY = "refresh_token"
    
    private lateinit var sharedPreferences: SharedPreferences
    
    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, accessToken)
            .putString(REFRESH_TOKEN_KEY, refreshToken)
            .apply()
    }
    
    fun saveAccessToken(accessToken: String) {
        sharedPreferences.edit()
            .putString(TOKEN_KEY, accessToken)
            .apply()
    }
    
    fun getAccessToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
    
    fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
    }
    
    fun clearTokens() {
        sharedPreferences.edit()
            .remove(TOKEN_KEY)
            .remove(REFRESH_TOKEN_KEY)
            .apply()
    }
    
    fun hasValidToken(): Boolean {
        return !getAccessToken().isNullOrEmpty()
    }
}