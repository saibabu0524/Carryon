package com.saibabui.auth.domain.usecases

class ValidateMobileOTP {
    fun execute(otp: String): ValidationResult {
        return if (otp.isBlank()) {
            ValidationResult(isSuccessful = false, errorMessage = "OTP number cannot be blank.")
        } else if (otp.length != 4) {
            ValidationResult(
                isSuccessful = false,
                errorMessage = "Entered OTP should be of 4 digits"
            )
        } else {
            ValidationResult(isSuccessful = true, errorMessage = "")
        }
    }
}