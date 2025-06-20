package com.saibabui.network.auth.api

import com.saibabui.network.auth.model.LoginRequest
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.SignUpRequest
import com.saibabui.network.auth.model.TestResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("/")
    suspend fun root(): TestResponse

    @POST("/api/auth/signup")
    suspend fun signUpWithEmail(
        @Body signUpRequest: SignUpRequest
    ): LoginResponse

    @POST("/api/auth/login")
    suspend fun loginWithEmail(@Body request: LoginRequest): LoginResponse
}