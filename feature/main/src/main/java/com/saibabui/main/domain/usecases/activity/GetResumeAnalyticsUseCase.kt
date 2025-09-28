package com.saibabui.main.domain.usecases.activity

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetResumeAnalyticsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(resumeId: Int) = 
        homeRepository.getResumeAnalytics(resumeId)
}