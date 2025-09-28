package com.saibabui.main.domain.usecases.resume

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class FilterResumesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(filters: Map<String, String>, page: Int = 1, limit: Int = 10) = 
        homeRepository.filterResumes(filters, page, limit)
}