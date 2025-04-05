package com.saibabui.auth.domain.usecases

class ValidateMobileNumber {
    fun execute(mobile: String): ValidationResult {
        return if (mobile.isBlank()) {
            ValidationResult(isSuccessful = false, errorMessage = "Mobile number cannot be blank.")
        } else if (mobile.length <= 9) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Entered mobile number is invalid."
            )
        } else {
            ValidationResult(isSuccessful = true, errorMessage = "")
        }
    }
}