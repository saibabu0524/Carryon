package com.saibabui.main.presentation.ui.components.resume

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
import com.saibabui.main.presentation.viewmodels.resume.ResumeViewModel
import com.saibabui.main.presentation.viewmodels.resume.ResumeUiState
import com.saibabui.network.home.model.ResumeResponse

@Composable
fun ResumeListScreen(
    viewModel: ResumeViewModel = hiltViewModel(),
    onNavigateToCreateResume: () -> Unit,
    onNavigateToEditResume: (ResumeResponse) -> Unit,
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateResume
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Resume"
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
                uiState.isLoading && uiState.resumes.isEmpty() -> {
                    LoadingIndicator()
                }
                uiState.error != null && uiState.resumes.isEmpty() -> {
                    ErrorScreen(
                        errorMessage = uiState.error!!,
                        onRetry = { viewModel.loadResumes() }
                    )
                }
                else -> {
                    ResumeListContent(
                        uiState = uiState,
                        onRefresh = { viewModel.loadResumes() },
                        onEditClick = onNavigateToEditResume,
                        onDeleteClick = { resume -> viewModel.deleteResume(resume.id) },
                        onShareClick = { resume -> 
                            // Handle share action
                            println("Sharing resume: ${resume.title}")
                        },
                        onItemClick = onNavigateToEditResume,
                        onCreateClick = onNavigateToCreateResume
                    )
                }
            }
        }
    }
}

@Composable
private fun ResumeListContent(
    uiState: ResumeUiState,
    onRefresh: () -> Unit,
    onEditClick: (ResumeResponse) -> Unit,
    onDeleteClick: (ResumeResponse) -> Unit,
    onShareClick: (ResumeResponse) -> Unit,
    onItemClick: (ResumeResponse) -> Unit,
    onCreateClick: () -> Unit,
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
                    text = "My Resumes",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                
                if (uiState.resumes.isNotEmpty()) {
                    Text(
                        text = "${uiState.resumes.size} resumes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Search bar
        item {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { query -> 
                    // Handle search
                },
                label = { Text("Search resumes") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (uiState.searchQuery.isNotBlank()) {
                        IconButton(
                            onClick = { 
                                // Clear search
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                }
            )
        }
        
        // Resume items
        if (uiState.resumes.isEmpty()) {
            item {
                if (!uiState.isLoading) {
                    EmptyState(
                        onRetry = onRefresh
                    )
                }
            }
        } else {
            items(uiState.resumes) { resume ->
                ResumeListItem(
                    resume = resume,
                    onClick = onItemClick,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick,
                    onShareClick = onShareClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // Loading indicator for pagination
            if (uiState.isLoading && uiState.resumes.isNotEmpty()) {
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
            Text("Loading resumes...")
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
            imageVector = Icons.Default.Description,
            contentDescription = "No resumes",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "No resumes found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "Create your first resume to get started",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}