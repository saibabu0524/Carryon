package com.saibabui.auth.presentation.ui.changepassword

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
class ChangePasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _oldPassword = MutableStateFlow(com.saibabui.ui.CustomTextFieldState())
    val oldPassword = _oldPassword

    private val _newPassword = MutableStateFlow(com.saibabui.ui.CustomTextFieldState())
    val newPassword = _newPassword

    private val _confirmNewPassword = MutableStateFlow(com.saibabui.ui.CustomTextFieldState())
    val confirmNewPassword = _confirmNewPassword

    private val _changePasswordState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val changePasswordState: StateFlow<UiState<Any>> = _changePasswordState.asStateFlow()

    fun setOldPassword(value: String) {
        _oldPassword.value = _oldPassword.value.copy(value = value)
    }

    fun setNewPassword(value: String) {
        _newPassword.value = _newPassword.value.copy(value = value)
    }

    fun setConfirmNewPassword(value: String) {
        _confirmNewPassword.value = _confirmNewPassword.value.copy(value = value)
    }

    fun submit() {
        if (_newPassword.value.value != _confirmNewPassword.value.value) {
            _changePasswordState.value = UiState.Error("New passwords do not match")
            return
        }

        viewModelScope.launch {
            authRepository.changePassword(
                com.saibabui.network.auth.model.ChangePasswordRequest(
                    oldPassword = _oldPassword.value.value,
                    newPassword = _newPassword.value.value
                )
            ).collectLatest { response ->
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
}