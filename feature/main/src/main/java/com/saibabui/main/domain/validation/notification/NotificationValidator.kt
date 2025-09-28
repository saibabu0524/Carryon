package com.saibabui.main.domain.validation.notification

import com.saibabui.main.domain.validation.ValidationResult

class NotificationValidator {
    fun validateNotificationType(notificationType: String?): ValidationResult {
        return when {
            notificationType != null && notificationType.length > 50 -> ValidationResult(
                isSuccessful = false,
                errorMessage = "Notification type cannot exceed 50 characters"
            )
            else -> ValidationResult(isSuccessful = true)
        }
    }
}