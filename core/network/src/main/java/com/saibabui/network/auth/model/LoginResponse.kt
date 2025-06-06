package com.saibabui.network.auth.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class LoginResponse(
    val status: String,
    val message: String,
    val data: LoginData
) : Parcelable


@Parcelize
data class LoginData(
    val accessToken: String,
    val refreshToken: String
) : Parcelable