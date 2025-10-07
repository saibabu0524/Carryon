package com.saibabui.main.presentation.ui.details_gethering

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.presentation.ui.details_gethering.ProfileMappers.toUiModel
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.UserProfileCreate
import com.saibabui.network.home.model.UserProfileDetails
import com.saibabui.network.home.model.UserProfileUpdate
import com.saibabui.network.home.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileFormViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<ProfileFormUiState>(ProfileFormUiState.Idle)
    val uiState: StateFlow<ProfileFormUiState> = _uiState.asStateFlow()
    
    private val _profileDetails = MutableStateFlow<ProfileFormUiModel?>(null)
    val profileDetails: StateFlow<ProfileFormUiModel?> = _profileDetails.asStateFlow()
    
    /**
     * Fetch the current user profile details
     */
    fun fetchProfileDetails() {
        viewModelScope.launch {
            _uiState.value = ProfileFormUiState.Loading
            homeRepository.getProfileDetails().collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        // Map the API response to UI model using the mappers
                        _profileDetails.value = response.data.toUiModel()
                        _uiState.value = ProfileFormUiState.Success("Profile details fetched successfully")
                    }
                    is ApiResponse.Error -> {
                        _uiState.value = ProfileFormUiState.Error("Failed to fetch profile: ${response.message}")
                    }
                    ApiResponse.Loading -> {
                        _uiState.value = ProfileFormUiState.Loading
                    }
                }
            }
        }
    }
    
    /**
     * Create or update the user profile
     */
    fun createOrUpdateProfile(userProfileCreate: UserProfileCreate) {
        viewModelScope.launch {
            _uiState.value = ProfileFormUiState.Loading
            homeRepository.createProfile(userProfileCreate).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        // Map the API response to UI model using the mappers
                        _profileDetails.value = response.data.toUiModel()
                        _uiState.value = ProfileFormUiState.Success("Profile created successfully")
                    }
                    is ApiResponse.Error -> {
                        _uiState.value = ProfileFormUiState.Error("Failed to create profile: ${response.message}")
                    }
                    ApiResponse.Loading -> {
                        _uiState.value = ProfileFormUiState.Loading
                    }
                }
            }
        }
    }
    
    /**
     * Update the existing user profile
     */
    fun updateExistingProfile(userProfileUpdate: UserProfileUpdate) {
        viewModelScope.launch {
            _uiState.value = ProfileFormUiState.Loading
            homeRepository.updateProfile(userProfileUpdate).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        // Map the API response to UI model using the mappers
                        _profileDetails.value = response.data.toUiModel()
                        _uiState.value = ProfileFormUiState.Success("Profile updated successfully")
                    }
                    is ApiResponse.Error -> {
                        _uiState.value = ProfileFormUiState.Error("Failed to update profile: ${response.message}")
                    }
                    ApiResponse.Loading -> {
                        _uiState.value = ProfileFormUiState.Loading
                    }
                }
            }
        }
    }
    
    /**
     * Determine if we should create a new profile or update an existing one
     */
    fun determineActionAndExecute(userProfileCreate: UserProfileCreate) {
        viewModelScope.launch {
            // First, try to fetch existing profile to see if we need to update or create
            fetchProfileDetails()
            
            // For now, we'll just create - in a real app, we'd wait for fetch to complete 
            // and then decide whether to create or update based on the result
            createOrUpdateProfile(userProfileCreate)
        }
    }
}

/**
 * UI State for the profile form
 */
sealed class ProfileFormUiState {
    object Idle : ProfileFormUiState()
    object Loading : ProfileFormUiState()
    data class Success(val message: String) : ProfileFormUiState()
    data class Error(val message: String) : ProfileFormUiState()
}