package com.saibabui.auth.presentation.ui.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.auth.utils.UiState
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _password = MutableStateFlow(com.saibabui.ui.CustomTextFieldState())
    val password = _password

    private val _confirmPassword = MutableStateFlow(com.saibabui.ui.CustomTextFieldState())
    val confirmPassword = _confirmPassword

    private val _resetPasswordState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val resetPasswordState: StateFlow<UiState<Any>> = _resetPasswordState.asStateFlow()

    private val _token = MutableStateFlow("")

    fun setPassword(value: String) {
        _password.value = _password.value.copy(value = value)
    }

    fun setConfirmPassword(value: String) {
        _confirmPassword.value = _confirmPassword.value.copy(value = value)
    }

    fun setToken(value: String) {
        _token.value = value
    }

    fun submit() {
        if (_password.value.value != _confirmPassword.value.value) {
            _resetPasswordState.value = UiState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            authRepository.resetPassword(
                com.saibabui.network.auth.model.ResetPasswordRequest(
                    token = _token.value,
                    newPassword = _password.value.value
                )
            ).collectLatest { response ->
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
}