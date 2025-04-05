package com.saibabui.network.auth.repositories

import com.saibabui.network.auth.api.AuthService
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.LoginRequest
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.TestResponse
import com.saibabui.network.auth.model.VerifyOTPRequest
import com.saibabui.network.auth.model.VerifyOTPResponse
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository, BaseRepository() {
    override fun testServer(): Flow<ApiResponse<TestResponse>> {
        return apiCall { authService.root() }
    }

    override fun loginWithMobileNumber(mobileNumber: String): Flow<ApiResponse<LoginResponse>> {
        return apiCall { authService.loginWithMobileNumber(LoginRequest(mobileNumber)) }
    }

    override fun verifyMobileOtp(verifyOTPRequest: VerifyOTPRequest): Flow<ApiResponse<VerifyOTPResponse>> {
        return apiCall {
            authService.verifyMobileOTP(verifyOTPRequest)
        }
    }
}
