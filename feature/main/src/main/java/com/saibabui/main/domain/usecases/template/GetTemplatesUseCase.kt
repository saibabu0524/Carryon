package com.saibabui.main.domain.usecases.template

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetTemplatesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        categoryId: Int? = null,
        page: Int = 1,
        pageSize: Int = 10
    ) = homeRepository.getTemplates(categoryId, page, pageSize)
}