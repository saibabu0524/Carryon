package com.saibabui.main.presentation.viewmodels.collaboration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.domain.usecases.collaboration.AddCollaboratorUseCase
import com.saibabui.main.domain.usecases.collaboration.GetCollaboratorsUseCase
import com.saibabui.main.domain.usecases.collaboration.RemoveCollaboratorUseCase
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.Collaborator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollaborationViewModel @Inject constructor(
    private val addCollaboratorUseCase: AddCollaboratorUseCase,
    private val getCollaboratorsUseCase: GetCollaboratorsUseCase,
    private val removeCollaboratorUseCase: RemoveCollaboratorUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollaborationUiState())
    val uiState: StateFlow<CollaborationUiState> = _uiState.asStateFlow()

    fun loadCollaborators(resumeId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                resumeId = resumeId
            )
            
            getCollaboratorsUseCase(resumeId).collect { response ->
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
                            collaborators = response.data.map { mapToCollaborator(it) }
                        )
                    }
                }
            }
        }
    }

    fun addCollaborator(resumeId: Int, email: String, role: String = "viewer") {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isAddingCollaborator = true,
                error = null,
                successMessage = null
            )
            
            addCollaboratorUseCase(resumeId, email, role).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isAddingCollaborator = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isAddingCollaborator = true)
                    }
                    is ApiResponse.Success -> {
                        // Reload collaborators to show the new one
                        loadCollaborators(resumeId)
                        _uiState.value = _uiState.value.copy(
                            isAddingCollaborator = false,
                            showAddCollaboratorDialog = false,
                            successMessage = "Collaborator added successfully"
                        )
                    }
                }
            }
        }
    }

    fun removeCollaborator(resumeId: Int, userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isRemovingCollaborator = true,
                error = null,
                successMessage = null
            )
            
            removeCollaboratorUseCase(resumeId, userId).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isRemovingCollaborator = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isRemovingCollaborator = true)
                    }
                    is ApiResponse.Success -> {
                        // Remove the collaborator from the list
                        val updatedCollaborators = _uiState.value.collaborators.filter { 
                            it.userId != userId 
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            isRemovingCollaborator = false,
                            collaborators = updatedCollaborators,
                            showRemoveConfirmationDialog = false,
                            collaboratorToRemove = null,
                            successMessage = "Collaborator removed successfully"
                        )
                    }
                }
            }
        }
    }

    fun showAddCollaboratorDialog() {
        _uiState.value = _uiState.value.copy(showAddCollaboratorDialog = true)
    }

    fun hideAddCollaboratorDialog() {
        _uiState.value = _uiState.value.copy(showAddCollaboratorDialog = false)
    }

    fun showRemoveConfirmationDialog(collaborator: Collaborator) {
        _uiState.value = _uiState.value.copy(
            showRemoveConfirmationDialog = true,
            collaboratorToRemove = collaborator
        )
    }

    fun confirmRemoveCollaborator() {
        _uiState.value.collaboratorToRemove?.let { collaborator ->
            _uiState.value.resumeId?.let { resumeId ->
                removeCollaborator(resumeId, collaborator.userId)
            }
        }
    }

    fun cancelRemoveCollaborator() {
        _uiState.value = _uiState.value.copy(
            showRemoveConfirmationDialog = false,
            collaboratorToRemove = null
        )
    }

    fun refreshCollaborators() {
        _uiState.value.resumeId?.let { loadCollaborators(it) }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }

    private fun mapToCollaborator(map: Map<String, Any>): Collaborator {
        return Collaborator(
            userId = map["user_id"] as? Int ?: 0,
            email = map["email"] as? String ?: "",
            role = map["role"] as? String ?: "viewer",
            joinedAt = map["joined_at"] as? String ?: ""
        )
    }
}