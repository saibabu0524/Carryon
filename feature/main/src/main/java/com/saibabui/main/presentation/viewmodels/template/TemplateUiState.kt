package com.saibabui.main.presentation.viewmodels.template

import com.saibabui.network.home.model.ResumeTemplateResponse

data class TemplateUiState(
    val isLoading: Boolean = false,
    val templates: List<ResumeTemplateResponse> = emptyList(),
    val myTemplates: List<ResumeTemplateResponse> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null,
    val isCreatingTemplate: Boolean = false,
    val isRefreshing: Boolean = false
)