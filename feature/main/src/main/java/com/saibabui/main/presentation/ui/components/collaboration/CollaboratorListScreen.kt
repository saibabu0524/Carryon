package com.saibabui.main.presentation.ui.components.collaboration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saibabui.main.presentation.viewmodels.collaboration.CollaborationViewModel
import com.saibabui.main.presentation.viewmodels.collaboration.CollaborationUiState
import com.saibabui.network.home.model.Collaborator

@Composable
fun CollaboratorListScreen(
    viewModel: CollaborationViewModel = hiltViewModel(),
    onResumeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Handle state changes
    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            // Show error snackbar
            println("Error: $errorMessage")
        }
    }
    
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { successMessage ->
            // Show success snackbar
            println("Success: $successMessage")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Collaborators",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.refreshCollaborators() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.showAddCollaboratorDialog() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add collaborator"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && uiState.collaborators.isEmpty() -> {
                    LoadingIndicator()
                }
                uiState.error != null && uiState.collaborators.isEmpty() -> {
                    ErrorScreen(
                        errorMessage = uiState.error!!,
                        onRetry = { viewModel.loadCollaborators() }
                    )
                }
                else -> {
                    CollaboratorListContent(
                        uiState = uiState,
                        onRefresh = { viewModel.refreshCollaborators() },
                        onRemoveClick = { collaborator -> 
                            viewModel.showRemoveConfirmationDialog(collaborator) 
                        },
                        onAddCollaborator = { email, role -> 
                            viewModel.addCollaborator(email, role)
                        },
                        onConfirmRemove = { viewModel.confirmRemoveCollaborator() },
                        onCancelRemove = { viewModel.cancelRemoveCollaborator() }
                    )
                }
            }
        }
    }
}

@Composable
private fun CollaboratorListContent(
    uiState: CollaborationUiState,
    onRefresh: () -> Unit,
    onRemoveClick: (Collaborator) -> Unit,
    onAddCollaborator: (String, String) -> Unit,
    onConfirmRemove: () -> Unit,
    onCancelRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Collaborators",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                
                if (uiState.collaborators.isNotEmpty()) {
                    Text(
                        text = "${uiState.collaborators.size} people",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Collaborator items
        if (uiState.collaborators.isEmpty()) {
            item {
                if (!uiState.isLoading) {
                    EmptyState(
                        onRetry = onRefresh
                    )
                }
            }
        } else {
            items(uiState.collaborators) { collaborator ->
                CollaboratorListItem(
                    collaborator = collaborator,
                    onRemoveClick = onRemoveClick,
                    onRoleChange = { _, _ -> /* Handle role change */ },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // Loading indicator for pagination
            if (uiState.isLoading && uiState.collaborators.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
    
    // Add collaborator dialog
    if (uiState.showAddCollaboratorDialog) {
        AddCollaboratorDialog(
            onAddCollaborator = onAddCollaborator,
            onDismiss = { viewModel.hideAddCollaboratorDialog() },
            isLoading = uiState.isAddingCollaborator
        )
    }
    
    // Remove confirmation dialog
    if (uiState.showRemoveConfirmationDialog) {
        AlertDialog(
            onDismissRequest = onCancelRemove,
            title = {
                Text(
                    text = "Remove Collaborator",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text("Are you sure you want to remove this collaborator from the resume?")
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirmRemove,
                    enabled = !uiState.isRemovingCollaborator
                ) {
                    if (uiState.isRemovingCollaborator) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Remove")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = onCancelRemove) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun AddCollaboratorDialog(
    onAddCollaborator: (String, String) -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("viewer") }
    var emailError by remember { mutableStateOf<String?>(null) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add Collaborator",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        emailError = null
                    },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = emailError != null,
                    supportingText = {
                        if (emailError != null) {
                            Text(
                                text = emailError!!,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                
                // Role selection
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Role",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = role == "viewer",
                            onClick = { role = "viewer" },
                            label = { Text("Viewer") }
                        )
                        
                        FilterChip(
                            selected = role == "editor",
                            onClick = { role = "editor" },
                            label = { Text("Editor") }
                        )
                        
                        FilterChip(
                            selected = role == "owner",
                            onClick = { role = "owner" },
                            label = { Text("Owner") }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Validate email
                    if (email.isBlank()) {
                        // In a real app, you'd use proper email validation
                        return@TextButton
                    }
                    onAddCollaborator(email, role)
                },
                enabled = !isLoading && email.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Add")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator()
            Text("Loading collaborators...")
        }
    }
}

@Composable
private fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyState(
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.People,
            contentDescription = "No collaborators",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "No collaborators",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "Add collaborators to work together on this resume",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Refresh")
        }
    }
}