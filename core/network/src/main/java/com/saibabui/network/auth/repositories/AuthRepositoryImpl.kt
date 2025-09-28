package com.saibabui.network.auth.repositories

import com.saibabui.network.auth.api.AuthService
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.auth.model.ChangePasswordRequest
import com.saibabui.network.auth.model.ForgotPasswordRequest
import com.saibabui.network.auth.model.MessageResponse
import com.saibabui.network.auth.model.RefreshTokenRequest
import com.saibabui.network.auth.model.ResetPasswordRequest
import com.saibabui.network.auth.model.SuccessResponse
import com.saibabui.network.auth.model.TokenResponse
import com.saibabui.network.auth.model.UserCreate
import com.saibabui.network.auth.model.UserLogin
import com.saibabui.network.home.model.ProfileResponse
import com.saibabui.network.utils.BaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import org.json.JSONException
import org.json.JSONObject

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository, BaseRepository() {

    // Custom apiCall for handling SuccessResponse wrapper
    private fun <T> apiCallWithSuccessResponse(apiCall: suspend () -> Response<SuccessResponse<T>>): Flow<ApiResponse<T>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val successResponse = response.body()
                if (successResponse != null && successResponse.data != null) {
                    emit(ApiResponse.Success(successResponse.data))
                } else {
                    emit(ApiResponse.Error("Empty response data"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = if (errorBody.isNullOrEmpty()) {
                    response.message()
                } else {
                    try {
                        JSONObject(errorBody).getString("message")
                    } catch (e: JSONException) {
                        response.message()
                    }
                }
                emit(ApiResponse.Error(errorMessage ?: "Unknown error", response.code()))
            }
        } catch (e: IOException) {
            emit(ApiResponse.Error("Network error, please try again"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }

    override suspend fun register(userCreate: UserCreate): Flow<ApiResponse<TokenResponse>> {
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

    override suspend fun getCurrentUser(): Flow<ApiResponse<ProfileResponse>> {
        return apiCallWithSuccessResponse { authService.getCurrentUser() }
    }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): Flow<ApiResponse<MessageResponse>> {
        return apiCall { authService.forgotPassword(forgotPasswordRequest) }
    }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): Flow<ApiResponse<MessageResponse>> {
        return apiCall { authService.resetPassword(resetPasswordRequest) }
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<ApiResponse<MessageResponse>> {
        return apiCall { authService.changePassword(changePasswordRequest) }
    }

    override suspend fun googleLogin(): Flow<ApiResponse<Map<String, Any>>> {
        return apiCallWithSuccessResponse { authService.googleLogin() }
    }

    override suspend fun googleCallback(code: String): Flow<ApiResponse<TokenResponse>> {
        return apiCall { authService.googleCallback(code) }
    }
}
