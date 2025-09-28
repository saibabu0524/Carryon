package com.saibabui.main.presentation.ui.components.notification

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
import com.saibabui.main.presentation.viewmodels.notification.NotificationViewModel
import com.saibabui.main.presentation.viewmodels.notification.NotificationUiState
import com.saibabui.network.home.model.NotificationResponse

@Composable
fun NotificationListScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    onNotificationClick: (NotificationResponse) -> Unit,
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
                        text = "Notifications",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    if (uiState.notifications.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.markAllNotificationsAsRead() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.DoneAll,
                                contentDescription = "Mark all as read"
                            )
                        }
                        
                        IconButton(
                            onClick = { viewModel.refreshNotifications() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
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
                uiState.isLoading && uiState.notifications.isEmpty() -> {
                    LoadingIndicator()
                }
                uiState.error != null && uiState.notifications.isEmpty() -> {
                    ErrorScreen(
                        errorMessage = uiState.error!!,
                        onRetry = { viewModel.loadNotifications() }
                    )
                }
                else -> {
                    NotificationListContent(
                        uiState = uiState,
                        onRefresh = { viewModel.refreshNotifications() },
                        onFilterChanged = { notificationType ->
                            viewModel.filterByType(notificationType)
                        },
                        onMarkAllAsRead = { viewModel.markAllNotificationsAsRead() },
                        onMarkAsRead = { notification -> 
                            viewModel.markNotificationAsRead(notification.id) 
                        },
                        onDelete = { notification -> 
                            viewModel.showDeleteConfirmation(notification) 
                        },
                        onItemClick = onNotificationClick,
                        onConfirmDelete = { viewModel.confirmDelete() },
                        onCancelDelete = { viewModel.cancelDelete() }
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationListContent(
    uiState: NotificationUiState,
    onRefresh: () -> Unit,
    onFilterChanged: (String?) -> Unit,
    onMarkAllAsRead: () -> Unit,
    onMarkAsRead: (NotificationResponse) -> Unit,
    onDelete: (NotificationResponse) -> Unit,
    onItemClick: (NotificationResponse) -> Unit,
    onConfirmDelete: () -> Unit,
    onCancelDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Filter chips
        if (uiState.notifications.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = uiState.selectedNotificationType == null,
                        onClick = { onFilterChanged(null) },
                        label = { Text("All") }
                    )
                }
                
                // Sample notification types - in reality this would come from available types
                val notificationTypes = listOf(
                    "resume_updated",
                    "collaborator_added", 
                    "profile_updated",
                    "security_alert",
                    "announcement"
                )
                
                items(notificationTypes) { notificationType ->
                    FilterChip(
                        selected = uiState.selectedNotificationType == notificationType,
                        onClick = { 
                            onFilterChanged(
                                if (uiState.selectedNotificationType == notificationType) null 
                                else notificationType
                            )
                        },
                        label = { 
                            Text(
                                text = notificationType.replace("_", " ").replaceFirstChar { it.uppercase() },
                                maxLines = 1
                            ) 
                        }
                    )
                }
            }
        }
        
        // Notification list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header with unread count
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Notifications",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    if (uiState.unreadCount > 0) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = "${uiState.unreadCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
            
            // Mark all as read button
            if (uiState.notifications.any { !it.isRead }) {
                item {
                    OutlinedButton(
                        onClick = onMarkAllAsRead,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mark all as read")
                    }
                }
            }
            
            // Notification items
            if (uiState.notifications.isEmpty()) {
                item {
                    if (!uiState.isLoading) {
                        EmptyState(
                            onRetry = onRefresh
                        )
                    }
                }
            } else {
                items(uiState.notifications) { notification ->
                    NotificationListItem(
                        notification = notification,
                        onClick = onItemClick,
                        onDeleteClick = onDelete,
                        onMarkAsReadClick = onMarkAsRead,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Loading indicator for pagination
                if (uiState.isLoading && uiState.notifications.isNotEmpty()) {
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
    
    // Delete confirmation dialog
    if (uiState.showDeleteDialog) {
        AlertDialog(
            onDismissRequest = onCancelDelete,
            title = {
                Text(
                    text = "Delete Notification",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text("Are you sure you want to delete this notification?")
            },
            confirmButton = {
                TextButton(onClick = onConfirmDelete) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = onCancelDelete) {
                    Text("Cancel")
                }
            }
        )
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
            Text("Loading notifications...")
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
            imageVector = Icons.Default.Notifications,
            contentDescription = "No notifications",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "No notifications",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "Your notifications will appear here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Refresh")
        }
    }
}