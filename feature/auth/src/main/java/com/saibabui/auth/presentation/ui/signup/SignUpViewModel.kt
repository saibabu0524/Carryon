package com.saibabui.auth.presentation.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.auth.domain.usecases.ValidateName
import com.saibabui.auth.domain.usecases.ValidateEmail
import com.saibabui.auth.domain.usecases.ValidatePassword
import com.saibabui.auth.domain.usecases.ValidateConfirmPassword
import com.saibabui.auth.utils.UiState
import com.saibabui.database.dao.UserDao
import com.saibabui.database.entities.User
import com.saibabui.datastore.UserPreferences
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.TokenResponse
import com.saibabui.network.auth.model.UserCreate
import com.saibabui.network.auth.repositories.AuthRepository
import com.saibabui.ui.CustomTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpResponse(
    val message: String,
    val userId: String
)

sealed class SignUpFormEvent {
    data class FirstNameChanged(val firstName: String) : SignUpFormEvent()
    data class LastNameChanged(val lastName: String) : SignUpFormEvent()
    data class EmailChanged(val email: String) : SignUpFormEvent()
    data class PasswordChanged(val password: String) : SignUpFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpFormEvent()
    object Submit : SignUpFormEvent()
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val validateConfirmPassword: ValidateConfirmPassword,
    private val authRepository: AuthRepository,
    private val dataStore: UserPreferences,
    private val userDao: UserDao
) : ViewModel() {

    private val _firstName = MutableStateFlow(CustomTextFieldState())
    val firstName = _firstName

    private val _lastName = MutableStateFlow(CustomTextFieldState())
    val lastName = _lastName

    private val _email = MutableStateFlow(CustomTextFieldState())
    val email = _email

    private val _password = MutableStateFlow(CustomTextFieldState())
    val password = _password

    private val _confirmPassword = MutableStateFlow(CustomTextFieldState())
    val confirmPassword = _confirmPassword

    private val _signUpState = MutableStateFlow<UiState<TokenResponse>>(UiState.Idle)
    val signUpState = _signUpState

    fun onEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.FirstNameChanged -> {
                val result = validateName.execute(event.firstName)
                _firstName.value = _firstName.value.copy(
                    value = event.firstName,
                    error = result.errorMessage,
                    isValid = result.isSuccessful
                )
            }

            is SignUpFormEvent.LastNameChanged -> {
                val result = validateName.execute(event.lastName)
                _lastName.value = _lastName.value.copy(
                    value = event.lastName,
                    error = result.errorMessage,
                    isValid = result.isSuccessful
                )
            }

            is SignUpFormEvent.EmailChanged -> {
                val result = validateEmail.execute(event.email)
                _email.value = _email.value.copy(
                    value = event.email,
                    error = result.errorMessage,
                    isValid = result.isSuccessful
                )
            }

            is SignUpFormEvent.PasswordChanged -> {
                val result = validatePassword.execute(event.password)
                _password.value = _password.value.copy(
                    value = event.password,
                    error = result.errorMessage,
                    isValid = result.isSuccessful
                )
                if (_confirmPassword.value.value.isNotEmpty()) {
                    val confirmResult = validateConfirmPassword.execute(
                        event.password,
                        _confirmPassword.value.value
                    )
                    _confirmPassword.value = _confirmPassword.value.copy(
                        error = confirmResult.errorMessage,
                        isValid = confirmResult.isSuccessful
                    )
                }
            }

            is SignUpFormEvent.ConfirmPasswordChanged -> {
                val result =
                    validateConfirmPassword.execute(_password.value.value, event.confirmPassword)
                _confirmPassword.value = _confirmPassword.value.copy(
                    value = event.confirmPassword,
                    error = result.errorMessage,
                    isValid = result.isSuccessful
                )
            }

            is SignUpFormEvent.Submit -> {
                if (_firstName.value.isValid && _lastName.value.isValid && _email.value.isValid && _password.value.isValid && _confirmPassword.value.isValid) {
                    register()
                }
            }
        }
    }

data class SignUpResponse(
    val data: TokenResponse
)

private fun register() {
    viewModelScope.launch {
        authRepository.register(
            UserCreate(
                fullName = _firstName.value.value + " " + _lastName.value.value,
            email = _email.value.value,
            password = _password.value.value
        )
        ).collectLatest { response ->
            when (response) {
                is ApiResponse.Loading -> _signUpState.value = UiState.Loading
                is ApiResponse.Success -> {
                    dataStore.updateAccessToken(response.data.data?.accessToken ?: "")
                    dataStore.updateRefreshToken(response.data.data?.refreshToken ?: "")
                    viewModelScope.launch(
                        Dispatchers.IO
                    ) {
                        userDao.insertAll(
                            User(
                                firstName = _firstName.value.value,
                                lastName = _lastName.value.value,
                                email = _email.value.value,
                                phone = null,
                                address = null
                            )
                        )
                    }
                    _signUpState.value = UiState.Success(response.data)
                }

                is ApiResponse.Error -> _signUpState.value = UiState.Error(response.message)
            }
        }
    }
}
}