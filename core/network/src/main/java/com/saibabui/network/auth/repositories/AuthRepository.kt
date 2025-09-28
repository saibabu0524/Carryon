package com.saibabui.network.auth.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.network.auth.model.TestResponse
import kotlinx.coroutines.flow.Flow


import com.saibabui.network.auth.model.ChangePasswordRequest
import com.saibabui.network.auth.model.ForgotPasswordRequest
import com.saibabui.network.auth.model.MessageResponse
import com.saibabui.network.auth.model.RefreshTokenRequest
import com.saibabui.network.auth.model.ResetPasswordRequest
import com.saibabui.network.auth.model.TokenResponse
import com.saibabui.network.auth.model.UserCreate
import com.saibabui.network.auth.model.UserLogin
import com.saibabui.network.home.model.UserProfileDetails

interface AuthRepository {
    suspend fun register(userCreate: UserCreate): Flow<ApiResponse<TokenResponse>>
    suspend fun login(userLogin: UserLogin): Flow<ApiResponse<TokenResponse>>
    suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Flow<ApiResponse<TokenResponse>>
    suspend fun logout(refreshTokenRequest: RefreshTokenRequest): Flow<ApiResponse<MessageResponse>>
//    suspend fun getCurrentUser(): Flow<ApiResponse<Map<String, Any>>>
    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Flow<ApiResponse<MessageResponse>>
    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ApiResponse<MessageResponse>>
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<ApiResponse<MessageResponse>>
    
    // Google login methods
    suspend fun googleLogin(): Flow<ApiResponse<Map<String, Any>>>
    suspend fun googleCallback(code: String): Flow<ApiResponse<TokenResponse>>
}
