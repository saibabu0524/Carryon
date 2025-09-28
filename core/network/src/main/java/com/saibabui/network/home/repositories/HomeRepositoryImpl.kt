package com.saibabui.network.home.repositories

import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.utils.BaseRepository
import com.saibabui.network.home.api.HomeService
import com.saibabui.network.home.model.UserProfileDetails
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository, BaseRepository() {
    override suspend fun getProfileDetails(): Flow<ApiResponse<UserProfileDetails>> {
        return apiCall { 
            homeService.getProfileDetails()
        }
    }
}
