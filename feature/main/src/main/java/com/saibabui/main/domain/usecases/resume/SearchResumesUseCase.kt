package com.saibabui.main.domain.usecases.resume

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class SearchResumesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(query: String, page: Int = 1, limit: Int = 10) = 
        homeRepository.searchResumes(query, page, limit)
}