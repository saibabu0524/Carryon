package com.saibabui.main.presentation.ui.transactions



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.database.entities.ResumeEntity
import com.saibabui.main.domain.ResumeRepository
import com.saibabui.ui.Resume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class RecentResumesViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow<Resume?>(null)
    val showDeleteDialog: StateFlow<Resume?> = _showDeleteDialog.asStateFlow()

    // Main resumes flow
    val resumes: StateFlow<List<Resume>> = _searchQuery
        .debounce(300) // Debounce search queries
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                resumeRepository.getAllResumes()
            } else {
                resumeRepository.searchResumes(query)
            }
        }
        .catch { exception ->
            _error.value = "Failed to load resumes: ${exception.message}"
            emit(emptyList())
        }
        .onStart {
            _isLoading.value = true
        }
        .onEach {
            _isLoading.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // Add a small delay for better UX
                delay(500)

                // Insert sample data if no resumes exist
                if (resumeRepository.getResumeCount() == 0) {
                    insertSampleResumes()
                }
            } catch (e: Exception) {
                _error.value = "Failed to initialize data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onResumeClick(resume: Resume) {
        // Handle resume click - navigate to edit screen
        // This will be implemented based on your navigation setup
    }

    fun onEditClick(resume: Resume) {
        // Handle edit click
        // Navigate to resume editor
    }

    fun onShareClick(resume: Resume) {
        // Handle share click
        // Implement sharing functionality
    }

    fun onDownloadClick(resume: Resume) {
        // Handle download click
        // Implement download functionality
    }

    fun onDeleteClick(resume: Resume) {
        _showDeleteDialog.value = resume
    }

    fun confirmDelete() {
        viewModelScope.launch {
            try {
                _showDeleteDialog.value?.let { resume ->
                    resumeRepository.deleteResume(resume)
                    _showDeleteDialog.value = null
                }
            } catch (e: Exception) {
                _error.value = "Failed to delete resume: ${e.message}"
            }
        }
    }

    fun cancelDelete() {
        _showDeleteDialog.value = null
    }

    fun toggleFavorite(resume: Resume) {
        viewModelScope.launch {
            try {
                resume.id?.let { id ->
                    // This would require adding favorite status to Resume data class
                    // For now, we'll just update in database
                    resumeRepository.updateFavoriteStatus(id, true)
                }
            } catch (e: Exception) {
                _error.value = "Failed to update favorite: ${e.message}"
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                delay(1000) // Simulate refresh
            } catch (e: Exception) {
                _error.value = "Failed to refresh: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    private suspend fun insertSampleResumes() {
        try {
            val sampleResumes = listOf(
                ResumeEntity(
                    id = "1",
                    title = "Software Engineer Resume",
                    templateName = "Modern Professional Template",
                    templateId = "modern_professional",
                    completionPercentage = 85,
                    lastModified = System.currentTimeMillis() - (1 * 24 * 60 * 60 * 1000),
                    createdAt = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000),
                    isCompleted = false
                ),
                ResumeEntity(
                    id = "2",
                    title = "Product Designer CV",
                    templateName = "Creative Portfolio Template",
                    templateId = "creative_portfolio",
                    completionPercentage = 92,
                    lastModified = System.currentTimeMillis() - (3 * 24 * 60 * 60 * 1000),
                    createdAt = System.currentTimeMillis() - (10 * 24 * 60 * 60 * 1000),
                    isCompleted = true
                ),
                ResumeEntity(
                    id = "3",
                    title = "Marketing Manager Resume",
                    templateName = "Simple Classic Template",
                    templateId = "simple_classic",
                    completionPercentage = 67,
                    lastModified = System.currentTimeMillis() - (5 * 24 * 60 * 60 * 1000),
                    createdAt = System.currentTimeMillis() - (15 * 24 * 60 * 60 * 1000),
                    isCompleted = false
                ),
                ResumeEntity(
                    id = "4",
                    title = "Data Scientist Portfolio",
                    templateName = "Technical Template",
                    templateId = "technical",
                    completionPercentage = 78,
                    lastModified = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000),
                    createdAt = System.currentTimeMillis() - (20 * 24 * 60 * 60 * 1000),
                    isCompleted = false
                ),
                ResumeEntity(
                    id = "5",
                    title = "Business Analyst CV",
                    templateName = "Corporate Template",
                    templateId = "corporate",
                    completionPercentage = 95,
                    lastModified = System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000),
                    createdAt = System.currentTimeMillis() - (12 * 24 * 60 * 60 * 1000),
                    isCompleted = true
                )
            )

            sampleResumes.forEach { resume ->
                resumeRepository.insertResumeEntity(resume)
            }
        } catch (e: Exception) {
            _error.value = "Failed to insert sample data: ${e.message}"
        }
    }
}