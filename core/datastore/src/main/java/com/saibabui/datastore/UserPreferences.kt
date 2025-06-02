package com.saibabui.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserPreferences {
    val accessToken: Flow<String>
    val refreshToken: Flow<String>
    val isDarkTheme: Flow<Boolean>

    suspend fun updateTheme(
        isDarkTheme: Boolean,
        preferenceKey: Preferences.Key<Boolean> = PreferenceKeys.IS_DARK_THEME
    )

    suspend fun updateAccessToken(
        accessToken: String,
        preferenceKey: Preferences.Key<String> = PreferenceKeys.ACCESS_TOKEN
    )

    suspend fun updateRefreshToken(
        accessToken: String,
        preferenceKey: Preferences.Key<String> = PreferenceKeys.REFRESH_TOKEN
    )
}