package com.saibabui.network.home.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.UserProfileDetails
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun getProfileDetails(): Flow<ApiResponse<UserProfileDetails>>
}
