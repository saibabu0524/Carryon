package com.saibabui.main.domain.usecases.profile

import com.saibabui.network.home.repositories.HomeRepository
import com.saibabui.network.home.model.ProfileUpdateRequest
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(request: ProfileUpdateRequest) = homeRepository.updateProfile(request)
}