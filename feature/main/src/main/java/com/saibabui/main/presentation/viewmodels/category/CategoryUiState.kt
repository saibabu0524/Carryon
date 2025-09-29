package com.saibabui.main.presentation.viewmodels.category

import com.saibabui.network.home.model.CategoryResponse
import com.saibabui.network.home.model.ResumeTemplateResponse

data class CategoryUiState(
    val isLoading: Boolean = false,
    val categories: List<CategoryResponse> = emptyList(),
    val templatesByCategory: List<ResumeTemplateResponse> = emptyList(),
    val selectedCategory: CategoryResponse? = null,
    val error: String? = null,
    val successMessage: String? = null,
    val isCreatingCategory: Boolean = false,
    val isUpdatingCategory: Boolean = false,
    val isDeletingCategory: Boolean = false,
    val isRefreshing: Boolean = false
)