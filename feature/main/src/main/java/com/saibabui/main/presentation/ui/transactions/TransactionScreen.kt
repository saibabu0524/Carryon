package com.saibabui.main.presentation.ui.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Composable
fun TransactionScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    ResumesScreen(navController = NavController(context = LocalContext.current))
}


@Composable
fun TransactionScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues),
        contentAlignment = Alignment.Center
    ) {
        ResumesScreen(navController = NavController(context = LocalContext.current))
    }
}




// Composable for each resume item
@Composable
fun ResumeItem(
    resume: Resume,
    onView: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = resume.name, style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = "Last modified: ${resume.lastModified}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onView) {
                Icon(Icons.Default.Visibility, contentDescription = "View Resume")
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Resume")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Resume")
            }
        }
    }
}

// Composable for delete confirmation dialog
@Composable
fun DeleteConfirmationDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Delete Resume") },
        text = { Text("Are you sure you want to delete this resume?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}

// Main ResumesScreen composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumesScreen(
    navController: NavController,
    viewModel: ResumesViewModel = viewModel()
) {
    val resumes by viewModel.resumes.collectAsState()
    var resumeToDelete by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Resumes") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("templates") }) {
                Icon(Icons.Default.Add, contentDescription = "Create New Resume")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (resumes.isEmpty()) {
                // Empty state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No resumes yet.", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("templates") }) {
                        Text("Create your first resume")
                    }
                }
            } else {
                // List of resumes
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(resumes) { resume ->
                        ResumeItem(
                            resume = resume,
                            onView = { navController.navigate("view_resume/${resume.id}") },
                            onEdit = { navController.navigate("edit_resume/${resume.id}") },
                            onDelete = { resumeToDelete = resume.id }
                        )
                    }
                }
            }

            // Delete confirmation dialog
            if (resumeToDelete != null) {
                DeleteConfirmationDialog(
                    onConfirm = {
                        viewModel.deleteResume(resumeToDelete!!)
                        resumeToDelete = null
                    },
                    onCancel = { resumeToDelete = null }
                )
            }
        }
    }
}