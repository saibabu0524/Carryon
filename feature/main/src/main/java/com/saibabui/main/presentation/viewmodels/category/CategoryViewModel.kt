package com.saibabui.main.presentation.viewmodels.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.domain.usecases.category.*
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.CategoryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getTemplatesByCategoryUseCase: GetTemplatesByCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getCategoriesUseCase().collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            categories = response.data
                        )
                    }
                }
            }
        }
    }

    fun createCategory(name: String, description: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isCreatingCategory = true,
                error = null,
                successMessage = null
            )
            
            createCategoryUseCase(name, description).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isCreatingCategory = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isCreatingCategory = true)
                    }
                    is ApiResponse.Success -> {
                        // Reload categories to show the new one
                        loadCategories()
                        _uiState.value = _uiState.value.copy(
                            isCreatingCategory = false,
                            successMessage = "Category created successfully"
                        )
                    }
                }
            }
        }
    }

    fun updateCategory(categoryId: Int, name: String, description: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isUpdatingCategory = true,
                error = null,
                successMessage = null
            )
            
            updateCategoryUseCase(categoryId, name, description).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isUpdatingCategory = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isUpdatingCategory = true)
                    }
                    is ApiResponse.Success -> {
                        // Reload categories to show the updated one
                        loadCategories()
                        _uiState.value = _uiState.value.copy(
                            isUpdatingCategory = false,
                            successMessage = "Category updated successfully"
                        )
                    }
                }
            }
        }
    }

    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isDeletingCategory = true,
                error = null,
                successMessage = null
            )
            
            deleteCategoryUseCase(categoryId).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isDeletingCategory = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isDeletingCategory = true)
                    }
                    is ApiResponse.Success -> {
                        // Reload categories to show the updated list
                        loadCategories()
                        _uiState.value = _uiState.value.copy(
                            isDeletingCategory = false,
                            successMessage = "Category deleted successfully"
                        )
                    }
                }
            }
        }
    }

    fun loadTemplatesByCategory(categoryId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getTemplatesByCategoryUseCase(categoryId).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            templatesByCategory = response.data
                        )
                    }
                }
            }
        }
    }

    fun selectCategory(category: CategoryResponse) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun refreshCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            loadCategories()
            _uiState.value = _uiState.value.copy(isRefreshing = false)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
}