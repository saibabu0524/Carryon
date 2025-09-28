package com.saibabui.main.navigation

import androidx.compose.runtime.*
import androidx.navigation.*
import com.saibabui.main.presentation.ui.components.resume.ResumeListScreen
import com.saibabui.main.presentation.viewmodels.resume.ResumeViewModel
import com.saibabui.network.home.model.ResumeResponse

@Composable
fun ResumeListScreen(
    onNavigateToCreateResume: () -> Unit,
    onNavigateToEditResume: (ResumeResponse) -> Unit
) {
    val viewModel: ResumeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
    ResumeListScreen(
        viewModel = viewModel,
        onNavigateToCreateResume = onNavigateToCreateResume,
        onNavigateToEditResume = onNavigateToEditResume
    )
}