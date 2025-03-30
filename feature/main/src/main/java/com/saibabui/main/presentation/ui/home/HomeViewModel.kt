package com.saibabui.main.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel to manage Home Screen data

// ViewModel
class HomeViewModel : ViewModel() {
    private val _userName = MutableStateFlow("John Doe")
    val userName: StateFlow<String> = _userName

    private val _recentResumes = MutableStateFlow<List<Resume>>(emptyList())
    val recentResumes: StateFlow<List<Resume>> = _recentResumes

    private val _resumeTips = MutableStateFlow(
        listOf(
            Tip("Tailor your resume to the job description."),
            Tip("Use action verbs to describe your achievements."),
            Tip("Keep your resume concise and to the point.")
        )
    )
    val resumeTips: StateFlow<List<Tip>> = _resumeTips

    private val _featuredTemplates = MutableStateFlow(
        listOf(
            Template("1", "Modern Resume", "Professional", "modern_preview.png"),
            Template("2", "Classic CV", "Traditional", "classic_preview.png"),
            Template("3", "Creative Portfolio", "Creative", "creative_preview.png"),
        )
    )
    val featuredTemplates: StateFlow<List<Template>> = _featuredTemplates

    init {
        viewModelScope.launch {
            // Simulate fetching data (replace with actual data source)
            _userName.value = "John Doe"
            _recentResumes.value = listOf(
                Resume("1", "Software Engineer Resume", "2023-10-01"),
                Resume("2", "Data Scientist Resume", "2023-09-15")
            )
        }
    }
}


// Data Classes
data class Resume(val id: String, val name: String, val lastModified: String)
data class Tip(val text: String)
data class Template(val id: String, val name: String, val category: String, val previewImage: String)