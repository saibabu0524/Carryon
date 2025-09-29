package com.saibabui.main.domain.usecases.category

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetTemplatesByCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        categoryId: Int,
        page: Int = 1,
        pageSize: Int = 10
    ) = homeRepository.getTemplatesByCategory(categoryId, page, pageSize)
}