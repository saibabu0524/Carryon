package com.saibabui.network.auth.model

data class LoginRequest(
    val mobileNumber: String
)


data class VerifyOTPRequest(
    val otp: String,
    val mobileNumber: String
)