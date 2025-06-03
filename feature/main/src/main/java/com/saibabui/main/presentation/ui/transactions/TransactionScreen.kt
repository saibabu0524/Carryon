// OptimizedRecentResumesScreen.kt
package com.saibabui.main.presentation.ui.transactions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saibabui.ui.ResumeCard
import com.saibabui.ui.shimmer.ShimmerResumeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentResumesScreen(
    navController: NavController,
    viewModel: RecentResumesViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val resumes by viewModel.resumes.collectAsState()
    val error by viewModel.error.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val showDeleteDialog by viewModel.showDeleteDialog.collectAsState()

    LaunchedEffect(Unit) {
        // Trigger initial data load if needed
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search Bar
        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::onSearchQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )

        // Content
        when {
            isLoading -> {
                ShimmerContent()
            }
            error != null -> {
                error?.let {
                    ErrorContent(
                        error = it,
                        onRetry = {
                            viewModel.clearError()
                            viewModel.refreshData()
                        }
                    )
                }
            }
            resumes.isEmpty() -> {
                EmptyContent(
                    searchQuery = searchQuery,
                    onCreateResume = {
                        // Navigate to create resume screen
                        // navController.navigate("create_resume")
                    }
                )
            }
            else -> {
                ResumesList(
                    resumes = resumes,
                    onResumeClick = viewModel::onResumeClick,
                    onEditClick = viewModel::onEditClick,
                    onShareClick = viewModel::onShareClick,
                    onDownloadClick = viewModel::onDownloadClick,
                    onDeleteClick = viewModel::onDeleteClick
                )
            }
        }
    }

    // Delete Confirmation Dialog
    showDeleteDialog?.let { resume ->
        DeleteConfirmationDialog(
            resumeTitle = resume.title,
            onConfirm = viewModel::confirmDelete,
            onCancel = viewModel::cancelDelete
        )
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search resumes...") },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = { onQueryChange("") }
                ) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        modifier = modifier
    )
}

@Composable
private fun ShimmerContent() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(6) { // Show 6 shimmer cards
            ShimmerResumeCard()
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Oops! Something went wrong",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Try Again")
        }
    }
}

@Composable
private fun EmptyContent(
    searchQuery: String,
    onCreateResume: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (searchQuery.isEmpty()) {
                "No resumes yet"
            } else {
                "No resumes found for \"$searchQuery\""
            },
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (searchQuery.isEmpty()) {
                "Create your first resume to get started"
            } else {
                "Try adjusting your search terms"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (searchQuery.isEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onCreateResume,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Create Resume")
            }
        }
    }
}

@Composable
private fun ResumesList(
    resumes: List<com.saibabui.ui.Resume>,
    onResumeClick: (com.saibabui.ui.Resume) -> Unit,
    onEditClick: (com.saibabui.ui.Resume) -> Unit,
    onShareClick: (com.saibabui.ui.Resume) -> Unit,
    onDownloadClick: (com.saibabui.ui.Resume) -> Unit,
    onDeleteClick: (com.saibabui.ui.Resume) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(
            items = resumes,
            key = { resume -> resume.id ?: resume.title }
        ) { resume ->
            EnhancedResumeCard(
                resume = resume,
                onClick = { onResumeClick(resume) },
                onEditClick = { onEditClick(resume) },
                onShareClick = { onShareClick(resume) },
                onDownloadClick = { onDownloadClick(resume) },
                onDeleteClick = { onDeleteClick(resume) }
            )
        }
    }
}

@Composable
private fun EnhancedResumeCard(
    resume: com.saibabui.ui.Resume,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onShareClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    ResumeCard(
        resume = resume,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        onClick = onClick,

    )
}

@Composable
private fun DeleteConfirmationDialog(
    resumeTitle: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = "Delete Resume",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete \"$resumeTitle\"? This action cannot be undone.",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}