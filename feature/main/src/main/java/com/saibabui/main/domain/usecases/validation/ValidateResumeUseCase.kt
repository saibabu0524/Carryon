package com.saibabui.main.domain.usecases.validation

import com.saibabui.main.domain.validation.ValidationResult
import com.saibabui.main.domain.validation.resume.ResumeValidator
import javax.inject.Inject

class ValidateResumeUseCase @Inject constructor(
    private val resumeValidator: ResumeValidator
) {
    operator fun invoke(
        title: String,
        content: String?,
        templateName: String?
    ): ValidationResult {
        // Validate title
        val titleResult = resumeValidator.validateTitle(title)
        if (!titleResult.isSuccessful) return titleResult

        // Validate content
        val contentResult = resumeValidator.validateContent(content)
        if (!contentResult.isSuccessful) return contentResult

        // Validate template name
        val templateResult = resumeValidator.validateTemplateName(templateName)
        if (!templateResult.isSuccessful) return templateResult

        return ValidationResult(isSuccessful = true)
    }
}