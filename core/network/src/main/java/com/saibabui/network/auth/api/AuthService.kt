package com.saibabui.network.auth.api

import com.saibabui.network.auth.model.ChangePasswordRequest
import com.saibabui.network.auth.model.ForgotPasswordRequest
import com.saibabui.network.auth.model.LoginRequest
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.MessageResponse
import com.saibabui.network.auth.model.RefreshTokenRequest
import com.saibabui.network.auth.model.ResetPasswordRequest
import com.saibabui.network.auth.model.SignUpRequest
import com.saibabui.network.auth.model.SuccessResponse
import com.saibabui.network.auth.model.TestResponse
import com.saibabui.network.auth.model.TokenResponse
import com.saibabui.network.auth.model.UserCreate
import com.saibabui.network.auth.model.UserLogin
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("/auth/register")
    suspend fun register(@Body userCreate: UserCreate): TokenResponse

    @POST("/auth/login")
    suspend fun login(@Body userLogin: UserLogin): TokenResponse

    @POST("/auth/refresh")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): TokenResponse

    @POST("/auth/logout")
    suspend fun logout(@Body refreshTokenRequest: RefreshTokenRequest): MessageResponse

//    @GET("/auth/me")
//    suspend fun getCurrentUser(): SuccessResponse<Map<String, Any>>

    @POST("/auth/forgot-password")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): MessageResponse

    @POST("/auth/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): MessageResponse

    @POST("/auth/change-password")
    suspend fun changePassword(@Body changePasswordRequest: ChangePasswordRequest): MessageResponse

    @GET("/auth/google/login")
    suspend fun googleLogin(): SuccessResponse<Map<String, Any>>

    @POST("/auth/google/callback")
    suspend fun googleCallback(@Query("code") code: String): TokenResponse

    @GET("/")
    suspend fun root(): Any

    @GET("/health")
    suspend fun healthCheck(): Any

}