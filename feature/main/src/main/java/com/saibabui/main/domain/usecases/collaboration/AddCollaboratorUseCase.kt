package com.saibabui.main.domain.usecases.collaboration

import com.saibabui.network.home.repositories.HomeRepository
import javax.inject.Inject

class AddCollaboratorUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(resumeId: Int, email: String, role: String = "viewer") = 
        homeRepository.addCollaborator(resumeId, email, role)
}