package com.saibabui.main.domain.usecases.category

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(categoryId: Int) = homeRepository.getCategory(categoryId)
}