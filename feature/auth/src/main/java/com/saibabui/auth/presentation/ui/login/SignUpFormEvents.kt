package com.saibabui.auth.presentation.ui.login

sealed class SignUpFormEvents {
    data class MobileOTPChangeEvent(val otp: String) : SignUpFormEvents()
    data object SignUpButtonEvent : SignUpFormEvents()
    data object OTPContinueButtonEvent : SignUpFormEvents()
}