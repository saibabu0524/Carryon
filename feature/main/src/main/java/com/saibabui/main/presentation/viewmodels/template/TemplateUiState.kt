package com.saibabui.main.presentation.viewmodels.template

data class TemplateUiState(
    val isLoading: Boolean = false,
    val templates: List<Map<String, Any>> = emptyList(),
    val myTemplates: List<Map<String, Any>> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null,
    val isCreatingTemplate: Boolean = false,
    val isRefreshing: Boolean = false,
    val selectedCategory: String? = null,
    val categories: List<String> = emptyList()
)