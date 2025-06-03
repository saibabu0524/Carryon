package com.saibabui.main.presentation.ui.templates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.ui.Template
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
    val selectedCategory: StateFlow<String?> = _selectedCategory

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
                Template(
                    id = "1",
                    name = "Modern Professional",
                    category = "Professional",
                    previewImage = "modern_preview.png",
                    description = "Clean and minimal design for corporate roles",
                    tags = listOf("Modern", "Professional")
                ),
                Template(
                    id = "2",
                    name = "Classic CV",
                    category = "Traditional",
                    previewImage = "classic_preview.png",
                    description = "Traditional layout for formal applications",
                    tags = listOf("Classic", "Traditional")
                ),
                Template(
                    id = "3",
                    name = "Creative Portfolio",
                    category = "Creative",
                    previewImage = "creative_preview.png",
                    description = "Stand out with this creative design",
                    tags = listOf("Creative", "Design")
                ),
                Template(
                    id = "4",
                    name = "Tech Resume",
                    category = "Professional",
                    previewImage = "tech_preview.png",
                    description = "Tech-focused design for IT professionals",
                    tags = listOf("Tech", "Professional")
                ),
                Template(
                    id = "5",
                    name = "Minimalist CV",
                    category = "Minimalist",
                    previewImage = "minimalist_preview.png",
                    description = "Simple and clean minimalist design",
                    tags = listOf("Minimalist", "Clean")
                )
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