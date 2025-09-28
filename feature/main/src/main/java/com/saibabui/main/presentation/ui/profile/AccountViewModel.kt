package com.saibabui.main.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.saibabui.auth.utils.UiState
import com.saibabui.datastore.UserPreferences
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.RefreshTokenRequest
import com.saibabui.network.auth.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

// ViewModel to manage account data and logic
@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: UserPreferences
) : ViewModel() {
    private val _userProfile = MutableStateFlow(
        UserProfile(
            name = "John Doe",
            email = "john.doe@example.com",
            phone = "123-456-7890",
            address = "123 Main St, City, Country",
            linkedin = "https://linkedin.com/in/johndoe",
            website = "https://johndoe.com",
            summary = "Experienced software engineer with a passion for developing innovative programs.",
            profilePictureUrl = "https://example.com/profile.jpg",
            experience = listOf(
                Experience(
                    title = "Software Engineer",
                    company = "Tech Corp",
                    duration = "2018 - Present",
                    description = "Developed and maintained web applications."
                )
            ),
            projects = listOf(
                Project(
                    name = "Project A",
                    description = "A web app for managing tasks.",
                    technologies = "Kotlin, Jetpack Compose"
                )
            ),
            internships = listOf(
                Internship(
                    title = "Intern",
                    company = "Startup Inc",
                    duration = "Summer 2017",
                    description = "Assisted in developing mobile applications."
                )
            )
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    fun updateProfile(updatedProfile: UserProfile) {
        _userProfile.value = updatedProfile
    }


    private val _isNotificationEnabled = MutableStateFlow(true)
    val isNotificationEnabled: StateFlow<Boolean> = _isNotificationEnabled

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> = _isDarkModeEnabled

    // Logout state
//    private val _logoutState = MutableStateFlow<UiState<Any>>(UiState.Idle)
//    val logoutState: StateFlow<UiState<Any>> = _logoutState.asStateFlow()

    // Update user profile details
    fun updateProfile(name: String, email: String) {
        _userProfile.value = _userProfile.value.copy(name = name, email = email)
    }

    // Toggle notification setting
    fun toggleNotification(enabled: Boolean) {
        _isNotificationEnabled.value = enabled
    }

    // Toggle dark mode setting
    fun toggleDarkMode(enabled: Boolean) {
        _isDarkModeEnabled.value = enabled
    }

    // Handle logout
    fun logout() {
        viewModelScope.launch {
            val refreshTokenFlow = dataStore.refreshToken
            val refreshToken = runBlocking { refreshTokenFlow.first() }
            authRepository.logout(RefreshTokenRequest(refreshToken)).collectLatest { response ->
                when (response) {
//                    is ApiResponse.Error -> {
//                        _logoutState.value = UiState.Error(response.message)
//                    }
//                    is ApiResponse.Loading -> {
//                        _logoutState.value = UiState.Loading
//                    }
//                    is ApiResponse.Success -> {
//                        _logoutState.value = UiState.Success(response.data)
//                        // Clear tokens from data store
//                        dataStore.updateAccessToken("")
//                        dataStore.updateRefreshToken("")
//                    }
                    is ApiResponse.Error -> {}
                    ApiResponse.Loading -> {}
                    is ApiResponse.Success<*> -> {}
                }
            }
        }
    }

    fun updateProfile(
        name: String,
        email: String,
        phone: String,
        address: String,
        linkedin: String,
        website: String,
        summary: String
    ) {
        _userProfile.value = _userProfile.value.copy(
            name = name,
            email = email,
            phone = phone,
            address = address,
            linkedin = linkedin,
            website = website,
            summary = summary
        )
    }
}

data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val linkedin: String,
    val website: String,
    val summary: String,
    val profilePictureUrl: String,
    val experience: List<Experience>,
    val projects: List<Project>,
    val internships: List<Internship>
)

data class Experience(
    val title: String,
    val company: String,
    val duration: String,
    val description: String
)

data class Project(
    val name: String,
    val description: String,
    val technologies: String
)

data class Internship(
    val title: String,
    val company: String,
    val duration: String,
    val description: String
)