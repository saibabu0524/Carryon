package com.saibabui.auth.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.auth.domain.usecases.ValidateMobileNumber
import com.saibabui.auth.domain.usecases.ValidateMobileOTP
import com.saibabui.auth.utils.UiState
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.VerifyOTPRequest
import com.saibabui.network.auth.model.VerifyOTPResponse
import com.saibabui.network.auth.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val validateMobileNumber: ValidateMobileNumber,
    private val validateMobileOTP: ValidateMobileOTP,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val TAG = this::class.simpleName

    private var _mobileNumber = MutableStateFlow(CustomTextFieldState())

    val mobileNumber = _mobileNumber

    private var _mobileOTP = MutableStateFlow(CustomTextFieldState())

    val mobileOTP = _mobileOTP

    private val _loginState = MutableStateFlow<UiState<LoginResponse>>(UiState.Idle)
    val loginState: StateFlow<UiState<LoginResponse>> = _loginState.asStateFlow()

    private val _otpScreenState = MutableStateFlow<UiState<VerifyOTPResponse>>(UiState.Idle)
    val otpScreenState: StateFlow<UiState<VerifyOTPResponse>> = _otpScreenState.asStateFlow()


    init {
        runBlocking {
            authRepository.testServer().collectLatest {
                println("test response + $it")
            }
        }
    }

    fun validate(event: SignUpFormEvents) {
        when (event) {
            is SignUpFormEvents.MobileNumberChangeEvent -> {
                val emailValidationResult = validateMobileNumber.execute(event.mobileNumber)
                mobileNumber.value =
                    mobileNumber.value.copy(
                        error = emailValidationResult.errorMessage,
                        value = event.mobileNumber,
                        isValid = emailValidationResult.isSuccessful
                    )
            }

            SignUpFormEvents.SignUpButtonEvent -> {
                if (mobileNumber.value.isValid) {
                    loginWithMobileNumber()
                } else {
                    validateMobileNumber.execute(mobileNumber.value.value)
                }
            }

            is SignUpFormEvents.MobileOTPChangeEvent -> {
                val otpValidationResult = validateMobileOTP.execute(event.otp)
                mobileOTP.value =
                    mobileOTP.value.copy(
                        error = otpValidationResult.errorMessage,
                        value = event.otp,
                        isValid = otpValidationResult.isSuccessful
                    )
            }

            SignUpFormEvents.OTPContinueButtonEvent -> {
                if (mobileOTP.value.isValid) {
                    verifyMobileOtp(mobileOTP.value.value)
                } else {
                    validateMobileOTP.execute(mobileOTP.value.value)
                }
            }
        }
    }

    private fun loginWithMobileNumber(
        mobileNumber: String = this.mobileNumber.value.value
    ) {
        viewModelScope.launch {
            authRepository.loginWithMobileNumber(mobileNumber).collectLatest {
                when (it) {
                    is ApiResponse.Error -> {
                        _loginState.value = UiState.Error(it.message)
                    }

                    ApiResponse.Loading -> {
                        _loginState.value = UiState.Loading
                    }

                    is ApiResponse.Success -> {
                        _loginState.value = UiState.Success(it.data)
                    }
                }
            }
        }
    }

    private fun verifyMobileOtp(
        otp: String
    ) {
        viewModelScope.launch {
            authRepository.verifyMobileOtp(VerifyOTPRequest(otp, mobileNumber.value.value))
                .collectLatest {
                    when (it) {
                        is ApiResponse.Error -> {
                            _otpScreenState.value = UiState.Error(it.message)
                        }

                        ApiResponse.Loading -> {
                            _otpScreenState.value = UiState.Loading
                        }

                        is ApiResponse.Success -> {
                            _otpScreenState.value = UiState.Success(it.data)
                        }
                    }
                }
        }
    }
}