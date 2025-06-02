package com.saibabui.auth.domain.usecases

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        return if (password.length < 6) {
            ValidationResult(false, "Password must be at least 6 characters")
        } else {
            ValidationResult(true)
        }
    }
}
