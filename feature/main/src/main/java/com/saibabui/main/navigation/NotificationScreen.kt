package com.saibabui.main.navigation

import androidx.compose.runtime.*
import com.saibabui.main.presentation.ui.components.notification.NotificationListScreen
import com.saibabui.main.presentation.viewmodels.notification.NotificationViewModel
import com.saibabui.network.home.model.NotificationResponse

@Composable
fun NotificationListScreen(
    onNotificationClick: (NotificationResponse) -> Unit
) {
    val viewModel: NotificationViewModel = androidx.hilt.navigation.compose.hiltViewModel()
    NotificationListScreen(
        viewModel = viewModel,
        onNotificationClick = onNotificationClick
    )
}