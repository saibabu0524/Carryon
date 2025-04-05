package com.saibabui.network.auth.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.TestResponse
import com.saibabui.network.auth.model.VerifyOTPRequest
import com.saibabui.network.auth.model.VerifyOTPResponse
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun testServer(): Flow<ApiResponse<TestResponse>>
    fun loginWithMobileNumber(mobileNumber : String): Flow<ApiResponse<LoginResponse>>
    fun verifyMobileOtp(verifyOTPRequest: VerifyOTPRequest): Flow<ApiResponse<VerifyOTPResponse>>
}
