package com.saibabui.network.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class VerifyOTPResponse(
    val status : String,
    val message : String,
    val data : VerifyOTPData
)


@Serializable
data class VerifyOTPData(
    val accessToken : String,
    val refreshToken : String
)
