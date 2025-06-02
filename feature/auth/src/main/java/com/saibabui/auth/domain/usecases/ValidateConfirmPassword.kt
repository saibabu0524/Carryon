package com.saibabui.auth.domain.usecases

class ValidateConfirmPassword {
    fun execute(password: String, confirmPassword: String): ValidationResult {
        return if (password != confirmPassword) {
            ValidationResult(false, "Passwords do not match")
        } else {
            ValidationResult(true)
        }
    }
}