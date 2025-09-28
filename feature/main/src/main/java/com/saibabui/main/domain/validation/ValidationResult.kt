package com.saibabui.main.domain.validation

data class ValidationResult(
    val isSuccessful: Boolean = false,
    val errorMessage: String = ""
)