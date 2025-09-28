package com.saibabui.main.domain.usecases.resume

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class DeleteResumeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(resumeId: Int) = homeRepository.deleteResume(resumeId)
}