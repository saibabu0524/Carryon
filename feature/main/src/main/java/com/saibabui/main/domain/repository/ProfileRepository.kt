package com.saibabui.main.domain.repository

import com.saibabui.network.home.model.*

interface ProfileRepository {
    suspend fun getProfileDetails(): Result<ProfileResponse>
    suspend fun updateProfile(request: ProfileUpdateRequest): Result<ProfileResponse>
    suspend fun uploadAvatar(file: java.io.File): Result<Unit>
    suspend fun deleteAvatar(): Result<Unit>
}