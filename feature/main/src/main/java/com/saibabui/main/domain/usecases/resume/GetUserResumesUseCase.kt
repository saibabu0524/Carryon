package com.saibabui.main.domain.usecases.resume

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetUserResumesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 10) = 
        homeRepository.getUserResumes(page, limit)
}