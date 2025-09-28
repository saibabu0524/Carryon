package com.saibabui.main.domain.validation.template

import com.saibabui.main.domain.validation.ValidationResult

class TemplateValidator {
    fun validateTemplateName(templateName: String): ValidationResult {
        return when {
            templateName.isBlank() -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Template name cannot be empty"
            )
            templateName.length > 100 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Template name cannot exceed 100 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

    fun validateTemplateDescription(description: String): ValidationResult {
        return when {
            description.length > 500 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Template description cannot exceed 500 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }

    fun validateCategory(category: String?): ValidationResult {
        return when {
            category != null && category.length > 50 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Category cannot exceed 50 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }
}