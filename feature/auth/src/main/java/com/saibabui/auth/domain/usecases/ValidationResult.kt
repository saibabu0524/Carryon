package com.saibabui.auth.domain.usecases

data class ValidationResult(
    val isSuccessful: Boolean = false,
    val errorMessage: String = ""
)