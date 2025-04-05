package com.saibabui.network.auth.api

import com.saibabui.network.auth.model.LoginRequest
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.TestResponse
import com.saibabui.network.auth.model.VerifyOTPRequest
import com.saibabui.network.auth.model.VerifyOTPResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("/")
    suspend fun root(): TestResponse

    @POST("/api/auth/generate-otp")
    suspend fun loginWithMobileNumber(@Body request: LoginRequest): LoginResponse

    @POST("/api/auth/verify-otp")
    suspend fun verifyMobileOTP(@Body request: VerifyOTPRequest): VerifyOTPResponse
}