package com.saibabui.main.domain.validation.collaboration

import com.saibabui.main.domain.validation.ValidationResult

class CollaborationValidator {
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Email cannot be empty"
            )
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Invalid email format"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

    fun validateRole(role: String): ValidationResult {
        return when {
            role.isBlank() -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Role cannot be empty"
            )
            role.length > 20 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Role cannot exceed 20 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }
}