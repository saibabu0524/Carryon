package com.saibabui.network.utils

import com.saibabui.network.auth.model.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    protected fun <T> apiCall(apiCall: suspend () -> Response<T>): Flow<ApiResponse<T>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResponse.Success(it))
                } ?: emit(ApiResponse.Error("Empty response body", response.code()))
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
                emit(ApiResponse.Error(errorMessage, response.code()))
            }
        } catch (e: IOException) {
            emit(ApiResponse.Error("Network error, please try again"))
        } catch (e: Exception) {
            emit(ApiResponse.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }
}

