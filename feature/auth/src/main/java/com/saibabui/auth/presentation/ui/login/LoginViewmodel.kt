package com.saibabui.auth.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.auth.domain.usecases.ValidateEmail
import com.saibabui.auth.domain.usecases.ValidatePassword
import com.saibabui.auth.utils.UiState
import com.saibabui.datastore.UserPreferences
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.TokenResponse
import com.saibabui.network.auth.model.UserLogin
import com.saibabui.network.auth.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    object Submit : LoginFormEvent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val authRepository: AuthRepository,
    private val dataStore: UserPreferences
) : ViewModel() {

    private val _email = MutableStateFlow(com.saibabui.ui.CustomTextFieldState())
    val email = _email

    private val _password = MutableStateFlow(com.saibabui.ui.CustomTextFieldState())
    val password = _password

    private val _loginState = MutableStateFlow<UiState<TokenResponse>>(UiState.Idle)
    val loginState: StateFlow<UiState<TokenResponse>> = _loginState.asStateFlow()

    fun validate(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                val emailValidationResult = validateEmail.execute(event.email)
                _email.value = _email.value.copy(
                    error = emailValidationResult.errorMessage,
                    value = event.email,
                    isValid = emailValidationResult.isSuccessful
                )
            }

            is LoginFormEvent.PasswordChanged -> {
                val passwordValidationResult = validatePassword.execute(event.password)
                _password.value = _password.value.copy(
                    error = passwordValidationResult.errorMessage,
                    value = event.password,
                    isValid = passwordValidationResult.isSuccessful
                )
            }

            is LoginFormEvent.Submit -> {
                if (_email.value.isValid && _password.value.isValid) {
                    login()
                } else {
                    validateEmail.execute(_email.value.value)
                    validatePassword.execute(_password.value.value)
                }
            }
        }
    }

private fun login() {
    viewModelScope.launch {
        authRepository.login(
            UserLogin(
            email = _email.value.value,
            password = _password.value.value
        )
        ).collectLatest {
            when (it) {
                is ApiResponse.Error -> {
                    _loginState.value = UiState.Error(it.message)
                }
                is ApiResponse.Loading -> {
                    _loginState.value = UiState.Loading
                }
                is ApiResponse.Success -> {
                    _loginState.value = UiState.Success(it.data)
                    it.data.data?.accessToken?.let { it1 -> dataStore.updateAccessToken(it1) }
                    it.data.data?.refreshToken?.let { it1 -> dataStore.updateRefreshToken(it1) }
                }
            }
        }
    }
}
}