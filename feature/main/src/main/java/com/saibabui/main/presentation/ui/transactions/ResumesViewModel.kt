package com.saibabui.main.presentation.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// Data class for Resume
data class Resume(val id: String, val name: String, val lastModified: String)

// ViewModel for ResumesScreen
class ResumesViewModel : ViewModel() {
    private val _resumes = MutableStateFlow<List<Resume>>(emptyList())
    val resumes: StateFlow<List<Resume>> = _resumes.asStateFlow()

    init {
        viewModelScope.launch {
            // Simulate fetching resumes (replace with actual data source)
            _resumes.value = listOf(
                Resume("1", "Software Engineer Resume", "2023-10-01"),
                Resume("2", "Data Scientist Resume", "2023-09-15")
            )
        }
    }

    fun deleteResume(id: String) {
        _resumes.value = _resumes.value.filter { it.id != id }
    }
}