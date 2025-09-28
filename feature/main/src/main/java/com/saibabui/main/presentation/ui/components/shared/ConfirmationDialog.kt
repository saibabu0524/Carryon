package com.saibabui.main.presentation.ui.components.shared

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmText: String = "Confirm",
    dismissText: String = "Cancel",
    isLoading: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(confirmText)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText)
            }
        },
        modifier = modifier
    )
}

@Composable
fun DeleteConfirmationDialog(
    itemName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    ConfirmationDialog(
        title = "Delete $itemName",
        message = "Are you sure you want to delete this $itemName? This action cannot be undone.",
        confirmText = "Delete",
        isLoading = isLoading,
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        modifier = modifier
    )
}