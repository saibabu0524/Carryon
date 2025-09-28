package com.saibabui.main.domain.validation.profile

import com.saibabui.main.domain.validation.ValidationResult

class ProfileValidator {
    fun validateFirstName(firstName: String): ValidationResult {
        return when {
            firstName.isBlank() -> ValidationResult(
                isSuccessful = false,
                errorMessage = "First name cannot be empty"
            )
            firstName.length > 50 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "First name cannot exceed 50 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

    fun validateLastName(lastName: String): ValidationResult {
        return when {
            lastName.isNotBlank() && lastName.length > 50 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Last name cannot exceed 50 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

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

    fun validatePhone(phone: String): ValidationResult {
        return when {
            phone.isNotBlank() && phone.length > 20 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Phone number cannot exceed 20 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

    fun validateBio(bio: String): ValidationResult {
        return when {
            bio.length > 500 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Bio cannot exceed 500 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

    fun validatePostalCode(postalCode: String): ValidationResult {
        return when {
            postalCode.isNotBlank() && postalCode.length > 10 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Postal code cannot exceed 10 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }
}