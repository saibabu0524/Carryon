package com.saibabui.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class UserPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
) : UserPreferences {
    override val accessToken: Flow<String>
        get() = dataStore.data
            .map { preferences ->
                preferences[PreferenceKeys.ACCESS_TOKEN] ?: ""
            }

    override val refreshToken: Flow<String>
        get() = dataStore.data
            .map { preferences ->
                preferences[PreferenceKeys.ACCESS_TOKEN] ?: ""
            }

    override val isDarkTheme: Flow<Boolean>
        get() = dataStore.data
            .map { preferences ->
                preferences[PreferenceKeys.IS_DARK_THEME] ?: false
            }

    override suspend fun updateTheme(
        isDarkTheme: Boolean,
        preferenceKey: Preferences.Key<Boolean>
    ) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = isDarkTheme
        }
    }

    override suspend fun updateAccessToken(
        accessToken: String,
        preferenceKey: Preferences.Key<String>
    ) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = accessToken
        }
        Log.d("token", "token updated")
    }

    override suspend fun updateRefreshToken(
        accessToken: String,
        preferenceKey: Preferences.Key<String>
    ) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = accessToken
        }
        print("token updated")

    }
}