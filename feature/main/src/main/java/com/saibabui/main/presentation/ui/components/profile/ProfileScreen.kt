package com.saibabui.main.presentation.ui.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saibabui.main.presentation.viewmodels.profile.ProfileViewModel
import com.saibabui.main.presentation.viewmodels.profile.ProfileUiState
import com.saibabui.network.home.model.ProfileUpdateRequest

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
//    val uiState by viewModel.uiState.collectAsState()
//
//    // Handle state changes and show appropriate UI
//    LaunchedEffect(uiState.error) {
//        uiState.error?.let { errorMessage ->
//            // Show error snackbar or dialog
//            println("Error: $errorMessage")
//        }
//    }
//
//    LaunchedEffect(uiState.successMessage) {
//        uiState.successMessage?.let { successMessage ->
//            // Show success snackbar or dialog
//            println("Success: $successMessage")
//        }
//    }
//
//    Box(modifier = modifier.fillMaxSize()) {
//        when {
//            uiState.isLoading -> {
//                LoadingIndicator()
//            }
//            uiState.error != null -> {
//                ErrorScreen(
//                    errorMessage = uiState.error!!,
//                    onRetry = { viewModel.loadProfile() }
//                )
//            }
//            else -> {
//                ProfileContent(
//                    uiState = uiState,
//                    onEditClick = { viewModel.toggleEditMode() },
//                    onSaveClick = { request -> viewModel.updateProfile(request) },
//                    onAvatarClick = { /* Handle avatar click */ },
//                    onDeleteAvatarClick = { /* Handle delete avatar */ }
//                )
//            }
//        }
//    }

    Text("Profile Screen is under construction")
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    onEditClick: () -> Unit,
    onSaveClick: (ProfileUpdateRequest) -> Unit,
    onAvatarClick: () -> Unit,
    onDeleteAvatarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(
            profile = uiState.profile,
            isEditing = uiState.isEditing,
            onEditClick = onEditClick,
            onAvatarClick = onAvatarClick,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        ProfileForm(
            profile = uiState.profile,
            isEditing = uiState.isEditing,
            onProfileUpdate = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        )
        
        if (uiState.isEditing) {
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = onDeleteAvatarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = uiState.profile?.avatarUrl?.isNotBlank() == true
            ) {
                Text("Delete Avatar")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        CircularProgressIndicator()
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
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}