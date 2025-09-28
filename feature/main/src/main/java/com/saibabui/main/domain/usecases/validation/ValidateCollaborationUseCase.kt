package com.saibabui.main.domain.usecases.validation

import com.saibabui.main.domain.validation.ValidationResult
import com.saibabui.main.domain.validation.collaboration.CollaborationValidator
import javax.inject.Inject

class ValidateCollaborationUseCase @Inject constructor(
    private val collaborationValidator: CollaborationValidator
) {
    operator fun invoke(
        email: String,
        role: String
    ): ValidationResult {
        // Validate email
        val emailResult = collaborationValidator.validateEmail(email)
        if (!emailResult.isSuccessful) return emailResult

        // Validate role
        val roleResult = collaborationValidator.validateRole(role)
        if (!roleResult.isSuccessful) return roleResult

        return ValidationResult(isSuccessful = true)
    }
}