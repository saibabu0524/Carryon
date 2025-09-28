package com.saibabui.main.navigation

import androidx.compose.runtime.*
import com.saibabui.main.presentation.ui.components.collaboration.CollaboratorListScreen
import com.saibabui.main.presentation.viewmodels.collaboration.CollaborationViewModel

@Composable
fun CollaboratorListScreen(
    onResumeSelected: (Int) -> Unit
) {
    val viewModel: CollaborationViewModel = androidx.hilt.navigation.compose.hiltViewModel()
    CollaboratorListScreen(
        viewModel = viewModel,
        onResumeSelected = onResumeSelected
    )
}