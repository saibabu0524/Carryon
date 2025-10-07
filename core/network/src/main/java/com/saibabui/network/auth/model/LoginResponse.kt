package com.saibabui.network.auth.model

import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: String,
    val message: String,
    val data: LoginData
)


@Serializable
data class LoginData(
    val accessToken: String,
    val refreshToken: String
)