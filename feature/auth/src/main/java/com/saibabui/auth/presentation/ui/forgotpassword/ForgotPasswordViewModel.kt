package com.saibabui.auth.presentation.ui.forgotpassword

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
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _email = MutableStateFlow(com.saibabui.ui.CustomTextFieldState())
    val email = _email

    private val _forgotPasswordState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val forgotPasswordState: StateFlow<UiState<Any>> = _forgotPasswordState.asStateFlow()

    fun setEmail(value: String) {
        _email.value = _email.value.copy(value = value)
    }

    fun submit() {
        viewModelScope.launch {
            authRepository.forgotPassword(com.saibabui.network.auth.model.ForgotPasswordRequest(_email.value.value))
                .collectLatest { response ->
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
}