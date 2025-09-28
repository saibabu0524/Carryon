package com.saibabui.main.domain.usecases.activity

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetActivityHistoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 10, actionType: String? = null) = 
        homeRepository.getActivityHistory(page, limit, actionType)
}