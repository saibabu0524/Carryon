package com.saibabui.main.presentation.viewmodels.validation

import androidx.lifecycle.ViewModel
import com.saibabui.main.domain.usecases.validation.ValidateProfileUseCase
import com.saibabui.main.domain.usecases.validation.ValidateResumeUseCase
import com.saibabui.main.domain.usecases.validation.ValidateTemplateUseCase
import com.saibabui.main.domain.validation.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val validateProfileUseCase: ValidateProfileUseCase,
    private val validateResumeUseCase: ValidateResumeUseCase,
    private val validateTemplateUseCase: ValidateTemplateUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ValidationUiState())
    val uiState: StateFlow<ValidationUiState> = _uiState.asStateFlow()

    fun validateProfile(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        bio: String,
        postalCode: String
    ): Boolean {
        val result = validateProfileUseCase(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            bio = bio,
            postalCode = postalCode
        )

        val errors = mutableMapOf<String, String>()
        if (!result.isSuccessful) {
            // In a real implementation, we would have field-specific validation
            errors["general"] = result.errorMessage
        }

        val hasErrors = !result.isSuccessful
        _uiState.value = _uiState.value.copy(
            profileErrors = errors,
            hasProfileErrors = hasErrors
        )

        return result.isSuccessful
    }

    fun validateResume(
        title: String,
        content: String?,
        templateName: String?
    ): Boolean {
        val result = validateResumeUseCase(
            title = title,
            content = content,
            templateName = templateName
        )

        val errors = mutableMapOf<String, String>()
        if (!result.isSuccessful) {
            errors["general"] = result.errorMessage
        }

        val hasErrors = !result.isSuccessful
        _uiState.value = _uiState.value.copy(
            resumeErrors = errors,
            hasResumeErrors = hasErrors
        )

        return result.isSuccessful
    }

    fun validateTemplate(
        templateName: String,
        description: String,
        category: String?
    ): Boolean {
        val result = validateTemplateUseCase(
            templateName = templateName,
            description = description,
            category = category
        )

        val errors = mutableMapOf<String, String>()
        if (!result.isSuccessful) {
            errors["general"] = result.errorMessage
        }

        val hasErrors = !result.isSuccessful
        _uiState.value = _uiState.value.copy(
            templateErrors = errors,
            hasTemplateErrors = hasErrors
        )

        return result.isSuccessful
    }

    fun clearProfileErrors() {
        _uiState.value = _uiState.value.copy(
            profileErrors = emptyMap(),
            hasProfileErrors = false
        )
    }

    fun clearResumeErrors() {
        _uiState.value = _uiState.value.copy(
            resumeErrors = emptyMap(),
            hasResumeErrors = false
        )
    }

    fun clearTemplateErrors() {
        _uiState.value = _uiState.value.copy(
            templateErrors = emptyMap(),
            hasTemplateErrors = false
        )
    }

    fun clearCollaborationErrors() {
        _uiState.value = _uiState.value.copy(
            collaborationErrors = emptyMap(),
            hasCollaborationErrors = false
        )
    }

    fun clearAllErrors() {
        _uiState.value = _uiState.value.copy(
            profileErrors = emptyMap(),
            resumeErrors = emptyMap(),
            templateErrors = emptyMap(),
            collaborationErrors = emptyMap(),
            hasProfileErrors = false,
            hasResumeErrors = false,
            hasTemplateErrors = false,
            hasCollaborationErrors = false
        )
    }
}