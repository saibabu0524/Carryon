package com.saibabui.main.domain.validation.resume

import com.saibabui.main.domain.validation.ValidationResult

class ResumeValidator {
    fun validateTitle(title: String): ValidationResult {
        return when {
            title.isBlank() -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Resume title cannot be empty"
            )
            title.length > 100 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Resume title cannot exceed 100 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

    fun validateContent(content: String?): ValidationResult {
        return when {
            content != null && content.length > 10000 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Resume content cannot exceed 10,000 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

    fun validateTemplateName(templateName: String?): ValidationResult {
        return when {
            templateName != null && templateName.length > 50 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Template name cannot exceed 50 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }
}