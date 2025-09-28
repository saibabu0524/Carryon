package com.saibabui.main.domain.usecases.validation

import com.saibabui.main.domain.validation.ValidationResult
import com.saibabui.main.domain.validation.template.TemplateValidator
import javax.inject.Inject

class ValidateTemplateUseCase @Inject constructor(
    private val templateValidator: TemplateValidator
) {
    operator fun invoke(
        templateName: String,
        description: String,
        category: String?
    ): ValidationResult {
        // Validate template name
        val nameResult = templateValidator.validateTemplateName(templateName)
        if (!nameResult.isSuccessful) return nameResult

        // Validate description
        val descriptionResult = templateValidator.validateTemplateDescription(description)
        if (!descriptionResult.isSuccessful) return descriptionResult

        // Validate category
        val categoryResult = templateValidator.validateCategory(category)
        if (!categoryResult.isSuccessful) return categoryResult

        return ValidationResult(isSuccessful = true)
    }
}