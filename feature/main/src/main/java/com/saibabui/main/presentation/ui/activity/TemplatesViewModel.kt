package com.saibabui.main.presentation.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.domain.TemplateRepository
import com.saibabui.ui.Template
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TemplatesUiState(
    val isLoading: Boolean = false,
    val templates: List<Template> = emptyList(),
    val categories: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val error: String? = null
)

@HiltViewModel
class TemplatesViewModel @Inject constructor(
    private val templateRepository: TemplateRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TemplatesUiState())
    val uiState: StateFlow<TemplatesUiState> = _uiState.asStateFlow()

    private val _allTemplates = MutableStateFlow<List<Template>>(emptyList())

    init {
        loadTemplates()
    }

    fun loadTemplates() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val templates = templateRepository.getAllTemplates()
                val categories = templates.map { it.category }.distinct().sorted()

                _allTemplates.value = templates
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    templates = templates,
                    categories = categories
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun filterByCategory(category: String?) {
        val filteredTemplates = if (category == null) {
            _allTemplates.value
        } else {
            _allTemplates.value.filter { it.category == category }
        }

        _uiState.value = _uiState.value.copy(
            templates = filteredTemplates,
            selectedCategory = category
        )
    }

    fun onTemplateSelected(template: Template) {
        viewModelScope.launch {
            try {
                templateRepository.markTemplateAsUsed(template.id)
            } catch (e: Exception) {
                // Log error but don't show to user as it's not critical
            }
        }
    }

    fun refreshTemplates() {
        loadTemplates()
    }
}