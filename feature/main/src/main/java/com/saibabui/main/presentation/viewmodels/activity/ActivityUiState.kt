package com.saibabui.main.presentation.viewmodels.activity

import com.saibabui.network.home.model.ActivityResponse

data class ActivityUiState(
    val isLoading: Boolean = false,
    val activities: List<ActivityResponse> = emptyList(),
    val dashboardStats: Map<String, Any> = emptyMap(),
    val resumeAnalytics: Map<String, Any> = emptyMap(),
    val error: String? = null,
    val successMessage: String? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val selectedActionType: String? = null,
    val isRefreshing: Boolean = false
)