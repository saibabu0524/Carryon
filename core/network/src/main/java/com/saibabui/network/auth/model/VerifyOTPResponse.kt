package com.saibabui.network.auth.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyOTPResponse(
    val status : String,
    val message : String,
    val data : VerifyOTPData
) : Parcelable


@Parcelize
data class VerifyOTPData(
    val accessToken : String,
    val refreshToken : String
) : Parcelable
