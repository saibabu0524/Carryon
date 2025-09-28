package com.saibabui.main.presentation.viewmodels.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.domain.usecases.template.GetTemplatesUseCase
import com.saibabui.main.domain.usecases.template.GetMyTemplatesUseCase
import com.saibabui.main.domain.usecases.template.CreateCustomTemplateUseCase
import com.saibabui.network.auth.model.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val getTemplatesUseCase: GetTemplatesUseCase,
    private val getMyTemplatesUseCase: GetMyTemplatesUseCase,
    private val createCustomTemplateUseCase: CreateCustomTemplateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TemplateUiState())
    val uiState: StateFlow<TemplateUiState> = _uiState.asStateFlow()

    init {
        loadTemplates()
        loadMyTemplates()
    }

    fun loadTemplates() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getTemplatesUseCase().collect { response ->
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
                        // Extract categories from templates
                        val categories = response.data.mapNotNull { 
                            it["category"] as? String 
                        }.distinct().sorted()
                        
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            templates = response.data,
                            categories = categories
                        )
                    }
                }
            }
        }
    }

    fun loadMyTemplates() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getMyTemplatesUseCase().collect { response ->
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
                            myTemplates = response.data
                        )
                    }
                }
            }
        }
    }

    fun createCustomTemplate(
        templateName: String,
        templateDescription: String,
        category: String?,
        file: MultipartBody.Part
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isCreatingTemplate = true,
                error = null,
                successMessage = null
            )
            
            createCustomTemplateUseCase(
                templateName = templateName,
                templateDescription = templateDescription,
                category = category,
                file = file
            ).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isCreatingTemplate = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isCreatingTemplate = true)
                    }
                    is ApiResponse.Success -> {
                        // Reload my templates to show the new one
                        loadMyTemplates()
                        _uiState.value = _uiState.value.copy(
                            isCreatingTemplate = false,
                            successMessage = "Template created successfully"
                        )
                    }
                }
            }
        }
    }

    fun filterByCategory(category: String?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun refreshTemplates() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            loadTemplates()
            loadMyTemplates()
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