package com.saibabui.main.presentation.viewmodels.resume

import com.saibabui.network.home.model.ResumeResponse

data class ResumeUiState(
    val isLoading: Boolean = false,
    val resumes: List<ResumeResponse> = emptyList(),
    val selectedResume: ResumeResponse? = null,
    val error: String? = null,
    val successMessage: String? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val isCreating: Boolean = false,
    val isUpdating: Boolean = false,
    val isDeleting: Boolean = false
)