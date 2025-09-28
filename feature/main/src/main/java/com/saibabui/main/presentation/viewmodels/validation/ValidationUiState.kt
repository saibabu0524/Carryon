package com.saibabui.main.presentation.viewmodels.validation

data class ValidationUiState(
    val profileErrors: Map<String, String> = emptyMap(),
    val resumeErrors: Map<String, String> = emptyMap(),
    val templateErrors: Map<String, String> = emptyMap(),
    val collaborationErrors: Map<String, String> = emptyMap(),
    val hasProfileErrors: Boolean = false,
    val hasResumeErrors: Boolean = false,
    val hasTemplateErrors: Boolean = false,
    val hasCollaborationErrors: Boolean = false
)