package com.saibabui.network.auth.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.TestResponse
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun testServer(): Flow<ApiResponse<TestResponse>>
    fun loginWithEmail(email: String, password: String): Flow<ApiResponse<LoginResponse>>
    fun signUpWithEmail(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flow<ApiResponse<LoginResponse>>
}
