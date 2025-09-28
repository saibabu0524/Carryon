package com.saibabui.main.domain.usecases.profile

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class DeleteAvatarUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = homeRepository.deleteAvatar()
}