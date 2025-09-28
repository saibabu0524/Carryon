package com.saibabui.main.domain.usecases.template

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetMyTemplatesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = homeRepository.getMyTemplates()
}