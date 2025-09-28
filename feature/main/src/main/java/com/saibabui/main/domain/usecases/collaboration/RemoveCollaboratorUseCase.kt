package com.saibabui.main.domain.usecases.collaboration

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class RemoveCollaboratorUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(resumeId: Int, userId: Int) = 
        homeRepository.removeCollaborator(resumeId, userId)
}