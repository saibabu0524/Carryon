package com.saibabui.network.home.api

import com.saibabui.network.home.model.UserProfileDetails
import retrofit2.http.GET

interface HomeService {
    @GET("/api/home/profile-details")
    suspend fun getProfileDetails(): UserProfileDetails
}