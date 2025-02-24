package com.saibabui.network.auth.model

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T?
)