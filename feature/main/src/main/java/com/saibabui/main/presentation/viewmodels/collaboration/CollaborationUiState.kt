package com.saibabui.main.presentation.viewmodels.collaboration

import com.saibabui.network.home.model.Collaborator

data class CollaborationUiState(
    val isLoading: Boolean = false,
    val collaborators: List<Collaborator> = emptyList(),
    val error: String? = null,
    val successMessage: String? = null,
    val isAddingCollaborator: Boolean = false,
    val isRemovingCollaborator: Boolean = false,
    val showAddCollaboratorDialog: Boolean = false,
    val showRemoveConfirmationDialog: Boolean = false,
    val collaboratorToRemove: Collaborator? = null,
    val resumeId: Int? = null
)