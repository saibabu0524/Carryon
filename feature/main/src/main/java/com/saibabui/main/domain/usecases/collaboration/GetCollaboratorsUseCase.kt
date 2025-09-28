package com.saibabui.main.domain.usecases.collaboration

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class GetCollaboratorsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(resumeId: Int) = 
        homeRepository.getCollaborators(resumeId)
}