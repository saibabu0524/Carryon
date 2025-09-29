package com.saibabui.main.presentation.ui.components.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.saibabui.main.presentation.viewmodels.activity.ActivityViewModel
import com.saibabui.main.presentation.viewmodels.activity.ActivityUiState
import com.saibabui.network.home.model.ActivityResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityListScreen(
    viewModel: ActivityViewModel = hiltViewModel(),
    onActivityClick: (ActivityResponse) -> Unit,
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
                        text = "Activity",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.refreshAll() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && uiState.activities.isEmpty() -> {
                    LoadingIndicator()
                }
                uiState.error != null && uiState.activities.isEmpty() -> {
                    ErrorScreen(
                        errorMessage = uiState.error!!,
                        onRetry = { viewModel.loadActivityHistory() }
                    )
                }
                else -> {
                    ActivityListContent(
                        uiState = uiState,
                        onRefresh = { viewModel.refreshAll() },
                        onFilterChanged = { actionType -> 
                            viewModel.filterByActionType(actionType)
                        },
                        onItemClick = onActivityClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityListContent(
    uiState: ActivityUiState,
    onRefresh: () -> Unit,
    onFilterChanged: (String?) -> Unit,
    onItemClick: (ActivityResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Filter chips
        if (uiState.activities.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = uiState.selectedActionType == null,
                        onClick = { onFilterChanged(null) },
                        label = { Text("All") }
                    )
                }
                
                // Sample action types - in reality this would come from available actions
                val actionTypes = listOf(
                    "resume_created",
                    "resume_updated", 
                    "resume_deleted",
                    "profile_updated",
                    "collaborator_added"
                )
                
                items(actionTypes) { actionType ->
                    FilterChip(
                        selected = uiState.selectedActionType == actionType,
                        onClick = { 
                            onFilterChanged(if (uiState.selectedActionType == actionType) null else actionType)
                        },
                        label = { 
                            Text(
                                text = actionType.replace("_", " ").replaceFirstChar { it.uppercase() },
                                maxLines = 1
                            ) 
                        }
                    )
                }
            }
        }
        
        // Activity list
        LazyColumn(
            modifier = Modifier
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
                        text = "Recent Activity",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    if (uiState.activities.isNotEmpty()) {
                        Text(
                            text = "${uiState.activities.size} activities",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Activity items
            if (uiState.activities.isEmpty()) {
                item {
                    if (!uiState.isLoading) {
                        EmptyState(
                            onRetry = onRefresh
                        )
                    }
                }
            } else {
                items(uiState.activities) { activity ->
                    ActivityListItem(
                        activity = activity,
                        onClick = onItemClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Loading indicator for pagination
                if (uiState.isLoading && uiState.activities.isNotEmpty()) {
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
            Text("Loading activity...")
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
            imageVector = Icons.Default.History,
            contentDescription = "No activity",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "No activity found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "Your activity will appear here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Refresh")
        }
    }
}