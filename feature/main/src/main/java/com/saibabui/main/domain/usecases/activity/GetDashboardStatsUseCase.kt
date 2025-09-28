package com.saibabui.main.domain.usecases.activity

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetDashboardStatsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = homeRepository.getDashboardStats()
}