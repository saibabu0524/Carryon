package com.saibabui.network.home.model

import com.google.gson.annotations.SerializedName
import com.saibabui.network.auth.model.ResponseStatus

/**
 * Generic SuccessResponse wrapper to match backend API specification
 */
data class SuccessResponse<T>(
    val status: ResponseStatus = ResponseStatus.SUCCESS,
    val message: String,
    val data: T?,
    @SerializedName("error_code")
    val errorCode: String? = null,
    val details: Any? = null
)