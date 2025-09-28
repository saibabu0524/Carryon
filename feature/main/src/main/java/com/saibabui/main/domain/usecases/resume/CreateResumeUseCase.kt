package com.saibabui.main.domain.usecases.resume

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class CreateResumeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        resumeTitle: String, 
        templateId: String? = null, 
        aiGenerate: Boolean = false
    ) = homeRepository.createResume(resumeTitle, templateId, aiGenerate)
}