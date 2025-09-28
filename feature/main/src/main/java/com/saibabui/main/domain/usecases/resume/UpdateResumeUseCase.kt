package com.saibabui.main.domain.usecases.resume

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class UpdateResumeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
        resumeId: Int,
        resumeTitle: String? = null,
        templateId: String? = null,
        content: String? = null
    ) = homeRepository.updateResume(resumeId, resumeTitle, templateId, content)
}