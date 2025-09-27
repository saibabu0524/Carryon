package com.saibabui.network.auth.model

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("old_password")
    val oldPassword: String,
    @SerializedName("new_password")
    val newPassword: String
)

data class ForgotPasswordRequest(
    val email: String
)

data class HTTPValidationError(
    val detail: List<ValidationError>
)

data class MessageResponse(
    val status: ResponseStatus = ResponseStatus.SUCCESS,
    val message: String,
    val data: Any? = null,
    @SerializedName("error_code")
    val errorCode: String? = null,
    val details: Any? = null
)

data class RefreshTokenRequest(
    @SerializedName("refresh_token")
    val refreshToken: String
)

data class ResetPasswordRequest(
    val token: String,
    @SerializedName("new_password")
    val newPassword: String
)

enum class ResponseStatus {
    @SerializedName("success")
    SUCCESS,
    @SerializedName("error")
    ERROR
}

data class SuccessResponse<T>(
    val status: ResponseStatus = ResponseStatus.SUCCESS,
    val message: String,
    val data: T? = null,
    @SerializedName("error_code")
    val errorCode: String? = null,
    val details: Any? = null
)

data class TokenResponse(
    val status: ResponseStatus = ResponseStatus.SUCCESS,
    val message: String,
    val data: TokenData? = null,
    @SerializedName("error_code")
    val errorCode: String? = null,
    val details: Any? = null
)

data class TokenData(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)

data class UserCreate(
    val email: String,
    val username: String? = null,
    @SerializedName("full_name")
    val fullName: String? = null,
    val password: String,
    val role: UserRole? = UserRole.USER
)

data class UserLogin(
    val email: String,
    val password: String
)

enum class UserRole {
    @SerializedName("admin")
    ADMIN,
    @SerializedName("user")
    USER
}

data class ValidationError(
    val loc: List<String>,
    val msg: String,
    val type: String
)