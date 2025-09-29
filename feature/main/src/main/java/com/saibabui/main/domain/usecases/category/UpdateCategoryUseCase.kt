package com.saibabui.main.domain.usecases.category

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        categoryId: Int,
        name: String,
        description: String? = null
    ) = homeRepository.updateCategory(
        categoryId,
        com.saibabui.network.home.model.CategoryCreate(
            name = name,
            description = description
        )
    )
}