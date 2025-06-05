package com.saibabui.main.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(
    private val dataStore : UserPreferences
) : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.isDarkTheme.collect {
                _isDarkTheme.value = it
            }
        }
    }

    fun toggleTheme() {
        val newThemeValue = !_isDarkTheme.value
        _isDarkTheme.value = newThemeValue
        viewModelScope.launch {
            dataStore.updateTheme(newThemeValue)
        }
    }
}
