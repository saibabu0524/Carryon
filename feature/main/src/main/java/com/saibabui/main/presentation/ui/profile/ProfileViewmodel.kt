package com.saibabui.main.presentation.ui.profile

import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(
    private val dataStore : UserPreferences
) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = dataStore.isDarkTheme
        .let { tokenFlow: Flow<Boolean> ->
            val loggedInFlow = MutableStateFlow(false)
            runBlocking {
                val token = tokenFlow.first()
                loggedInFlow.value = token
            }
            loggedInFlow
        }
        .asStateFlow()

    val isLoggedIn: StateFlow<Boolean>
        get() = dataStore.accessToken
            .let { tokenFlow: Flow<String> ->
                val loggedInFlow = MutableStateFlow(false)
                runBlocking {
                        val token = tokenFlow.first()
                        loggedInFlow.value = token.isNotEmpty()
                }
                loggedInFlow
            }
            .asStateFlow()


    fun toggleTheme() {
        val newThemeValue = !_isDarkTheme.value
        _isDarkTheme.value = newThemeValue
        viewModelScope.launch {
            dataStore.updateTheme(newThemeValue)
        }
    }
}
