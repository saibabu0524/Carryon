package com.saibabui.main.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.saibabui.main.presentation.ui.components.activity.ActivityListScreen
import com.saibabui.main.presentation.viewmodels.activity.ActivityViewModel
import com.saibabui.network.home.model.ActivityResponse

@Composable
fun ActivityListScreen(
    onActivityClick: (ActivityResponse) -> Unit
) {
    val viewModel: ActivityViewModel = androidx.hilt.navigation.compose.hiltViewModel()
    ActivityListScreen(
        viewModel = viewModel,
        onActivityClick = onActivityClick
    )
}