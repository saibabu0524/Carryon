package com.saibabui.main.presentation.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.presentation.ui.home.Template
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TemplatesViewModel : ViewModel() {
    private val _allTemplates = MutableStateFlow<List<Template>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<String?>(null)

    val templates: StateFlow<List<Template>> = combine(
        _allTemplates,
        _searchQuery,
        _selectedCategory
    ) { allTemplates, searchQuery, selectedCategory ->
        allTemplates.filter { template ->
            val matchesSearch = template.name.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == null || template.category == selectedCategory
            matchesSearch && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init {
        viewModelScope.launch {
            _allTemplates.value = listOf(
                Template("1", "Modern Resume", "Professional", "modern_preview.png"),
                Template("2", "Classic CV", "Traditional", "classic_preview.png"),
                Template("3", "Creative Portfolio", "Creative", "creative_preview.png"),
                Template("4", "Tech Resume", "Professional", "tech_preview.png"),
                Template("5", "Minimalist CV", "Minimalist", "minimalist_preview.png")
            )
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }
}