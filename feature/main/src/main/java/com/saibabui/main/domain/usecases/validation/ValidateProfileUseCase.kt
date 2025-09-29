package com.saibabui.main.domain.usecases.validation

import com.saibabui.main.domain.validation.ValidationResult
import com.saibabui.main.domain.validation.profile.ProfileValidator
import com.saibabui.main.domain.validation.resume.ResumeValidator
import com.saibabui.main.domain.validation.template.TemplateValidator
import com.saibabui.main.domain.validation.notification.NotificationValidator
import javax.inject.Inject

class ValidateProfileUseCase @Inject constructor(
    private val profileValidator: ProfileValidator
) {
    operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        bio: String,
        postalCode: String
    ): ValidationResult {
        // Validate each field
        val firstNameResult = profileValidator.validateFirstName(firstName)
        if (!firstNameResult.isSuccessful) return firstNameResult

        val lastNameResult = profileValidator.validateLastName(lastName)
        if (!lastNameResult.isSuccessful) return lastNameResult

        val emailResult = profileValidator.validateEmail(email)
        if (!emailResult.isSuccessful) return emailResult

        val phoneResult = profileValidator.validatePhone(phone)
        if (!phoneResult.isSuccessful) return phoneResult

        val bioResult = profileValidator.validateBio(bio)
        if (!bioResult.isSuccessful) return bioResult

        val postalCodeResult = profileValidator.validatePostalCode(postalCode)
        if (!postalCodeResult.isSuccessful) return postalCodeResult

        return ValidationResult(isSuccessful = true)
    }
}