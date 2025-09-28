package com.saibabui.main.presentation.ui.components.activity

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
import com.saibabui.network.home.model.ActivityResponse
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ActivityListItem(
    activity: ActivityResponse,
    onClick: (ActivityResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(activity) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Activity icon based on action type
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = getActionColor(activity.action),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getActionIcon(activity.action),
                    contentDescription = activity.action,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            // Activity details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Action and resume info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = activity.action.replace("_", " ").replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    if (activity.resumeId != null) {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = "Resume",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Details
                if (activity.details?.isNotBlank() == true) {
                    Text(
                        text = activity.details,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // Timestamp
                Text(
                    text = formatDate(activity.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun getActionIcon(action: String): androidx.compose.ui.graphics.vector.ImageVector {
    return when (action.lowercase()) {
        "resume_created" -> Icons.Default.Add
        "resume_updated" -> Icons.Default.Edit
        "resume_deleted" -> Icons.Default.Delete
        "profile_updated" -> Icons.Default.Person
        "template_applied" -> Icons.Default.Palette
        "collaborator_added" -> Icons.Default.Group
        "collaborator_removed" -> Icons.Default.GroupRemove
        "file_downloaded" -> Icons.Default.Download
        "file_shared" -> Icons.Default.Share
        else -> Icons.Default.Info
    }
}

private fun getActionColor(action: String): androidx.compose.ui.graphics.Color {
    return when (action.lowercase()) {
        "resume_created" -> MaterialTheme.colorScheme.primary
        "resume_updated" -> MaterialTheme.colorScheme.secondary
        "resume_deleted" -> MaterialTheme.colorScheme.error
        "profile_updated" -> MaterialTheme.colorScheme.tertiary
        "template_applied" -> MaterialTheme.colorScheme.primary
        "collaborator_added" -> MaterialTheme.colorScheme.secondary
        "collaborator_removed" -> MaterialTheme.colorScheme.error
        "file_downloaded" -> MaterialTheme.colorScheme.primary
        "file_shared" -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.primary
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}