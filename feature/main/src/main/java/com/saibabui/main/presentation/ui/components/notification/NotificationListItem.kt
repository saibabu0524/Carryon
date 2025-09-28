package com.saibabui.main.presentation.ui.components.notification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.saibabui.network.home.model.NotificationResponse
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NotificationListItem(
    notification: NotificationResponse,
    onClick: (NotificationResponse) -> Unit,
    onDeleteClick: (NotificationResponse) -> Unit,
    onMarkAsReadClick: (NotificationResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { 
                if (!notification.isRead) {
                    onMarkAsReadClick(notification)
                }
                onClick(notification)
            },
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.primaryContainer
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (notification.isRead) 1.dp else 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header with title and actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Notification title
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Timestamp
                Text(
                    text = formatDate(notification.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Notification message
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodyMedium,
                color = if (notification.isRead) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
            
            // Notification type badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Type badge
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = getNotificationTypeColor(notification.notificationType)
                    )
                ) {
                    Text(
                        text = notification.notificationType.replace("_", " ").replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (!notification.isRead) {
                        IconButton(
                            onClick = { onMarkAsReadClick(notification) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Mark as read",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    
                    IconButton(
                        onClick = { onDeleteClick(notification) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun getNotificationTypeColor(type: String): androidx.compose.ui.graphics.Color {
    return when (type.lowercase()) {
        "resume_updated" -> MaterialTheme.colorScheme.secondary
        "collaborator_added" -> MaterialTheme.colorScheme.tertiary
        "profile_updated" -> MaterialTheme.colorScheme.primary
        "security_alert" -> MaterialTheme.colorScheme.error
        "announcement" -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.primary
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}