package com.saibabui.main.domain.usecases.category

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class CreateCategoryUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String? = null
    ) = homeRepository.createCategory(
        com.saibabui.network.home.model.CategoryCreate(
            name = name,
            description = description
        )
    )
}