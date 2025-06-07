package com.saibabui.network.auth.repositories

import com.saibabui.network.auth.api.AuthService
import com.saibabui.network.utils.BaseRepository
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.LoginRequest
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.SignUpRequest
import com.saibabui.network.auth.model.TestResponse
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository, BaseRepository() {
    override fun testServer(): Flow<ApiResponse<TestResponse>> {
        return apiCall { authService.root() }
    }

    override fun loginWithEmail(email: String, password: String): Flow<ApiResponse<LoginResponse>> {
        return apiCall { authService.loginWithEmail(LoginRequest(email, password)) }
    }

    override fun signUpWithEmail(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flow<ApiResponse<LoginResponse>> {
        return apiCall {
            authService.signUpWithEmail(
                SignUpRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password
                )
            )
        }
    }
}
