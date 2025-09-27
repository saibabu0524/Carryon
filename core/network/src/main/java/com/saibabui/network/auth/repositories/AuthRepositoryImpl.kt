package com.saibabui.network.auth.repositories

import com.saibabui.network.auth.api.AuthService
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.ChangePasswordRequest
import com.saibabui.network.auth.model.ForgotPasswordRequest
import com.saibabui.network.auth.model.MessageResponse
import com.saibabui.network.auth.model.RefreshTokenRequest
import com.saibabui.network.auth.model.ResetPasswordRequest
import com.saibabui.network.auth.model.TokenResponse
import com.saibabui.network.auth.model.UserCreate
import com.saibabui.network.auth.model.UserLogin
import com.saibabui.network.utils.BaseRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository, BaseRepository() {

    override suspend fun
            register(userCreate: UserCreate): Flow<ApiResponse<TokenResponse>> {
        return apiCall { authService.register(userCreate) }
    }

    override suspend fun login(userLogin: UserLogin): Flow<ApiResponse<TokenResponse>> {
        return apiCall { authService.login(userLogin) }
    }

    override suspend fun refreshToken(refreshTokenRequest: RefreshTokenRequest): Flow<ApiResponse<TokenResponse>> {
        return apiCall { authService.refreshToken(refreshTokenRequest) }
    }

    override suspend fun logout(refreshTokenRequest: RefreshTokenRequest): Flow<ApiResponse<MessageResponse>> {
        return apiCall { authService.logout(refreshTokenRequest) }
    }

//    override suspend fun getCurrentUser(): Flow<ApiResponse<Map<String, Any>>> {
//        return apiCall { authService.getCurrentUser() }
//    }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Flow<ApiResponse<MessageResponse>> {
        return apiCall { authService.forgotPassword(forgotPasswordRequest) }
    }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ApiResponse<MessageResponse>> {
        return apiCall { authService.resetPassword(resetPasswordRequest) }
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<ApiResponse<MessageResponse>> {
        return apiCall { authService.changePassword(changePasswordRequest) }
    }
}
