package com.saibabui.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.auth.utils.UiState
import com.saibabui.datastore.UserPreferences
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.ChangePasswordRequest
import com.saibabui.network.auth.model.ForgotPasswordRequest
import com.saibabui.network.auth.model.MessageResponse
import com.saibabui.network.auth.model.RefreshTokenRequest
import com.saibabui.network.auth.model.ResetPasswordRequest
import com.saibabui.network.auth.model.TokenResponse
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

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStore: UserPreferences
) : ViewModel() {

    // Logout state
    private val _logoutState = MutableStateFlow<UiState<MessageResponse>>(UiState.Idle)
    val logoutState: StateFlow<UiState<MessageResponse>> = _logoutState.asStateFlow()

    // Forgot password state
    private val _forgotPasswordState = MutableStateFlow<UiState<MessageResponse>>(UiState.Idle)
    val forgotPasswordState: StateFlow<UiState<MessageResponse>> = _forgotPasswordState.asStateFlow()

    // Reset password state
    private val _resetPasswordState = MutableStateFlow<UiState<MessageResponse>>(UiState.Idle)
    val resetPasswordState: StateFlow<UiState<MessageResponse>> = _resetPasswordState.asStateFlow()

    // Change password state
    private val _changePasswordState = MutableStateFlow<UiState<MessageResponse>>(UiState.Idle)
    val changePasswordState: StateFlow<UiState<MessageResponse>> = _changePasswordState.asStateFlow()

    // Google login state
    private val _googleLoginState = MutableStateFlow<UiState<Map<String, Any>>>(UiState.Idle)
    val googleLoginState: StateFlow<UiState<Map<String, Any>>> = _googleLoginState.asStateFlow()

    // Google callback state
    private val _googleCallbackState = MutableStateFlow<UiState<TokenResponse>>(UiState.Idle)
    val googleCallbackState: StateFlow<UiState<TokenResponse>> = _googleCallbackState.asStateFlow()

    // Profile details state
    private val _profileDetailsState = MutableStateFlow<UiState<Any>>(UiState.Idle) // Using generic type since we need to import home repository
    val profileDetailsState: StateFlow<UiState<Any>> = _profileDetailsState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            val refreshTokenFlow = dataStore.refreshToken
            val refreshToken = runBlocking { refreshTokenFlow.first() }
            authRepository.logout(RefreshTokenRequest(refreshToken)).collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _logoutState.value = UiState.Error(response.message)
                    }
                    is ApiResponse.Loading -> {
                        _logoutState.value = UiState.Loading
                    }
                    is ApiResponse.Success -> {
                        _logoutState.value = UiState.Success(response.data)
                        // Clear tokens from data store
                        dataStore.updateAccessToken("")
                        dataStore.updateRefreshToken("")
                    }
                }
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            authRepository.forgotPassword(ForgotPasswordRequest(email)).collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _forgotPasswordState.value = UiState.Error(response.message)
                    }
                    is ApiResponse.Loading -> {
                        _forgotPasswordState.value = UiState.Loading
                    }
                    is ApiResponse.Success -> {
                        _forgotPasswordState.value = UiState.Success(response.data)
                    }
                }
            }
        }
    }

    fun resetPassword(token: String, newPassword: String) {
        viewModelScope.launch {
            authRepository.resetPassword(ResetPasswordRequest(token, newPassword)).collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _resetPasswordState.value = UiState.Error(response.message)
                    }
                    is ApiResponse.Loading -> {
                        _resetPasswordState.value = UiState.Loading
                    }
                    is ApiResponse.Success -> {
                        _resetPasswordState.value = UiState.Success(response.data)
                    }
                }
            }
        }
    }

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            authRepository.changePassword(ChangePasswordRequest(oldPassword, newPassword)).collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _changePasswordState.value = UiState.Error(response.message)
                    }
                    is ApiResponse.Loading -> {
                        _changePasswordState.value = UiState.Loading
                    }
                    is ApiResponse.Success -> {
                        _changePasswordState.value = UiState.Success(response.data)
                    }
                }
            }
        }
    }

    fun googleLogin() {
        viewModelScope.launch {
            authRepository.googleLogin().collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _googleLoginState.value = UiState.Error(response.message)
                    }
                    is ApiResponse.Loading -> {
                        _googleLoginState.value = UiState.Loading
                    }
                    is ApiResponse.Success -> {
                        _googleLoginState.value = UiState.Success(response.data)
                    }
                }
            }
        }
    }

    fun googleCallback(code: String) {
        viewModelScope.launch {
            authRepository.googleCallback(code).collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _googleCallbackState.value = UiState.Error(response.message)
                    }
                    is ApiResponse.Loading -> {
                        _googleCallbackState.value = UiState.Loading
                    }
                    is ApiResponse.Success -> {
                        _googleCallbackState.value = UiState.Success(response.data)
                        // Save tokens to data store
                        response.data.data?.accessToken?.let { dataStore.updateAccessToken(it) }
                        response.data.data?.refreshToken?.let { dataStore.updateRefreshToken(it) }
                    }
                }
            }
        }
    }
}