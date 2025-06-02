package com.saibabui.auth.domain.usecases

class ValidateName {
    fun execute(name: String): ValidationResult {
        return if (name.isBlank()) {
            ValidationResult(false, "Name cannot be empty")
        } else {
            ValidationResult(true)
        }
    }
}
