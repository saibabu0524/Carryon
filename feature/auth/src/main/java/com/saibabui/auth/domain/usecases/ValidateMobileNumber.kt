package com.saibabui.auth.domain.usecases

import android.util.Patterns

class ValidateMobileNumber {
    fun execute(mobile: String): ValidationResult {
        return if (mobile.isBlank()) {
            ValidationResult(isSuccessful = false, errorMessage = "Mobile number cannot be blank.")
        } else if (!Patterns.PHONE.matcher(mobile).matches()) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Entered mobile number is invalid."
            )
        } else {
            ValidationResult(isSuccessful = true, errorMessage = "")
        }
    }
}