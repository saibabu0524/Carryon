package com.saibabui.main.domain.usecases.validation

import com.saibabui.main.domain.validation.profile.ProfileValidator
import com.saibabui.main.domain.validation.resume.ResumeValidator
import com.saibabui.main.domain.validation.template.TemplateValidator
import com.saibabui.main.domain.validation.notification.NotificationValidator
import com.saibabui.main.domain.validation.category.CategoryValidator
import com.saibabui.main.domain.validation.ValidationResult
import javax.inject.Inject

data class ValidationErrors(
    val errors: Map<String, String> = emptyMap()
) {
    val isValid: Boolean get() = errors.isEmpty()
}

class ValidationUseCase @Inject constructor(
    private val profileValidator: ProfileValidator,
    private val resumeValidator: ResumeValidator,
    private val templateValidator: TemplateValidator,
    private val notificationValidator: NotificationValidator,
    private val categoryValidator: CategoryValidator
) {
    fun validateProfile(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        bio: String,
        postalCode: String
    ): ValidationErrors {
        val errors = mutableMapOf<String, String>()
        
        profileValidator.validateFirstName(firstName).takeIf { !it.isSuccessful }?.let {
            errors["firstName"] = it.errorMessage
        }
        
        profileValidator.validateLastName(lastName).takeIf { !it.isSuccessful }?.let {
            errors["lastName"] = it.errorMessage
        }
        
        profileValidator.validateEmail(email).takeIf { !it.isSuccessful }?.let {
            errors["email"] = it.errorMessage
        }
        
        profileValidator.validatePhone(phone).takeIf { !it.isSuccessful }?.let {
            errors["phone"] = it.errorMessage
        }
        
        profileValidator.validateBio(bio).takeIf { !it.isSuccessful }?.let {
            errors["bio"] = it.errorMessage
        }
        
        profileValidator.validatePostalCode(postalCode).takeIf { !it.isSuccessful }?.let {
            errors["postalCode"] = it.errorMessage
        }
        
        return ValidationErrors(errors)
    }
    
    fun validateResume(
        title: String,
        content: String?,
        templateName: String?
    ): ValidationErrors {
        val errors = mutableMapOf<String, String>()
        
        resumeValidator.validateTitle(title).takeIf { !it.isSuccessful }?.let {
            errors["title"] = it.errorMessage
        }
        
        resumeValidator.validateContent(content).takeIf { !it.isSuccessful }?.let {
            errors["content"] = it.errorMessage
        }
        
        resumeValidator.validateTemplateName(templateName).takeIf { !it.isSuccessful }?.let {
            errors["templateName"] = it.errorMessage
        }
        
        return ValidationErrors(errors)
    }
    
    fun validateTemplate(
        templateName: String,
        description: String,
        category: String?
    ): ValidationErrors {
        val errors = mutableMapOf<String, String>()
        
        templateValidator.validateTemplateName(templateName).takeIf { !it.isSuccessful }?.let {
            errors["templateName"] = it.errorMessage
        }
        
        templateValidator.validateTemplateDescription(description).takeIf { !it.isSuccessful }?.let {
            errors["description"] = it.errorMessage
        }
        
        templateValidator.validateCategory(category).takeIf { !it.isSuccessful }?.let {
            errors["category"] = it.errorMessage
        }
        
        return ValidationErrors(errors)
    }
    
    fun validateCategory(
        name: String,
        description: String?
    ): ValidationErrors {
        val errors = mutableMapOf<String, String>()
        
        categoryValidator.validateName(name).takeIf { !it.isSuccessful }?.let {
            errors["name"] = it.errorMessage
        }
        
        categoryValidator.validateDescription(description).takeIf { !it.isSuccessful }?.let {
            errors["description"] = it.errorMessage
        }
        
        return ValidationErrors(errors)
    }
}