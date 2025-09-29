package com.saibabui.main.domain.validation.category

import com.saibabui.main.domain.validation.ValidationResult
import javax.inject.Inject

class CategoryValidator @Inject constructor() {
    
    fun validateName(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Category name cannot be empty"
            )
        }
        
        if (name.length < 2) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Category name must be at least 2 characters long"
            )
        }
        
        if (name.length > 50) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Category name cannot exceed 50 characters"
            )
        }
        
        return ValidationResult(isSuccessful = true)
    }
    
    fun validateDescription(description: String?): ValidationResult {
        if (description != null && description.length > 200) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Category description cannot exceed 200 characters"
            )
        }
        
        return ValidationResult(isSuccessful = true)
    }
}