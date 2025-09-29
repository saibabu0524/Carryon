package com.saibabui.main.domain.usecases.template

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetMyTemplatesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        page: Int = 1,
        pageSize: Int = 10
    ) = homeRepository.getMyTemplates(page, pageSize)
}