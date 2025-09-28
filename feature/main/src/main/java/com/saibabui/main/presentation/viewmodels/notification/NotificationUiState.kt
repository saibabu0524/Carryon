package com.saibabui.main.presentation.viewmodels.notification

import com.saibabui.network.home.model.NotificationResponse

data class NotificationUiState(
    val isLoading: Boolean = false,
    val notifications: List<NotificationResponse> = emptyList(),
    val unreadCount: Int = 0,
    val error: String? = null,
    val successMessage: String? = null,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val selectedNotificationType: String? = null,
    val isRefreshing: Boolean = false,
    val isMarkingAsRead: Boolean = false,
    val isDeleting: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val notificationToDelete: NotificationResponse? = null
)