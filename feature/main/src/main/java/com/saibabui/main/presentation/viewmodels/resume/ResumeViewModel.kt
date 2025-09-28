package com.saibabui.main.presentation.viewmodels.resume

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.domain.usecases.resume.GetUserResumesUseCase
import com.saibabui.main.domain.usecases.resume.CreateResumeUseCase
import com.saibabui.main.domain.usecases.resume.UpdateResumeUseCase
import com.saibabui.main.domain.usecases.resume.DeleteResumeUseCase
import com.saibabui.main.domain.usecases.resume.SearchResumesUseCase
import com.saibabui.main.domain.usecases.resume.DownloadResumeUseCase
import com.saibabui.main.domain.usecases.resume.FilterResumesUseCase
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.ResumeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val getUserResumesUseCase: GetUserResumesUseCase,
    private val createResumeUseCase: CreateResumeUseCase,
    private val updateResumeUseCase: UpdateResumeUseCase,
    private val deleteResumeUseCase: DeleteResumeUseCase,
    private val searchResumesUseCase: SearchResumesUseCase,
    private val downloadResumeUseCase: DownloadResumeUseCase,
    private val filterResumesUseCase: FilterResumesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResumeUiState())
    val uiState: StateFlow<ResumeUiState> = _uiState.asStateFlow()

    init {
        loadResumes()
    }

    fun loadResumes(page: Int = 1) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getUserResumesUseCase(page = page, limit = 10).collect { response ->
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
                            resumes = response.data,
                            currentPage = page
                        )
                    }
                }
            }
        }
    }

    fun createResume(
        title: String,
        templateId: String? = null,
        aiGenerate: Boolean = false
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isCreating = true,
                error = null,
                successMessage = null
            )
            
            createResumeUseCase(
                resumeTitle = title,
                templateId = templateId,
                aiGenerate = aiGenerate
            ).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isCreating = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isCreating = true)
                    }
                    is ApiResponse.Success -> {
                        // Reload resumes to show the new one
                        loadResumes(_uiState.value.currentPage)
                        _uiState.value = _uiState.value.copy(
                            isCreating = false,
                            successMessage = "Resume created successfully",
                            selectedResume = response.data
                        )
                    }
                }
            }
        }
    }

    fun updateResume(
        resumeId: Int,
        title: String? = null,
        templateId: String? = null,
        content: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isUpdating = true,
                error = null,
                successMessage = null
            )
            
            updateResumeUseCase(
                resumeId = resumeId,
                resumeTitle = title,
                templateId = templateId,
                content = content
            ).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isUpdating = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isUpdating = true)
                    }
                    is ApiResponse.Success -> {
                        // Update the resume in the list
                        val updatedResumes = _uiState.value.resumes.map { resume ->
                            if (resume.id == response.data.id) response.data else resume
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            isUpdating = false,
                            resumes = updatedResumes,
                            selectedResume = response.data,
                            successMessage = "Resume updated successfully"
                        )
                    }
                }
            }
        }
    }

    fun deleteResume(resumeId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isDeleting = true,
                error = null,
                successMessage = null
            )
            
            deleteResumeUseCase(resumeId).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isDeleting = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isDeleting = true)
                    }
                    is ApiResponse.Success -> {
                        // Remove the deleted resume from the list
                        val updatedResumes = _uiState.value.resumes.filter { 
                            it.id != resumeId 
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            isDeleting = false,
                            resumes = updatedResumes,
                            successMessage = "Resume deleted successfully"
                        )
                    }
                }
            }
        }
    }

    fun searchResumes(query: String, page: Int = 1) {
        if (query.isBlank()) {
            loadResumes(page)
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSearching = true,
                searchQuery = query,
                error = null
            )
            
            searchResumesUseCase(query = query, page = page, limit = 10).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isSearching = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isSearching = true)
                    }
                    is ApiResponse.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isSearching = false,
                            resumes = response.data,
                            currentPage = page
                        )
                    }
                }
            }
        }
    }

    fun downloadResume(resumeId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            downloadResumeUseCase(resumeId).collect { response ->
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
                            successMessage = "Download link generated successfully"
                        )
                        // Here we would typically trigger the actual download
                        // This could be done through a callback or event
                    }
                }
            }
        }
    }

    fun filterResumes(filters: Map<String, String>, page: Int = 1) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            filterResumesUseCase(filters = filters, page = page, limit = 10).collect { response ->
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
                            resumes = response.data,
                            currentPage = page
                        )
                    }
                }
            }
        }
    }

    fun selectResume(resume: ResumeResponse?) {
        _uiState.value = _uiState.value.copy(selectedResume = resume)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }

    fun clearSearch() {
        _uiState.value = _uiState.value.copy(searchQuery = "", isSearching = false)
        loadResumes()
    }
}