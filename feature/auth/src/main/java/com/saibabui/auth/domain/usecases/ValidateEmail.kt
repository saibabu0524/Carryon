package com.saibabui.auth.domain.usecases

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        return if (!email.contains("@") || email.isBlank()) {
            ValidationResult(false, "Invalid email format")
        } else {
            ValidationResult(true)
        }
    }
}
