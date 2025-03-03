package com.saibabui.network.auth.repositories

import com.saibabui.network.auth.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

abstract class BaseRepository {

    protected fun <T> apiCall(apiCall: suspend () -> T): Flow<ApiResponse<T>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiCall()
            emit(ApiResponse.Success(response))
            println("api hit step-3")
        } catch (e: HttpException) {
            emit(ApiResponse.Error(e.message ?: "HTTP error", e.code()))
        } catch (e: IOException) {
            emit(ApiResponse.Error("Network error, please try again"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }
}

