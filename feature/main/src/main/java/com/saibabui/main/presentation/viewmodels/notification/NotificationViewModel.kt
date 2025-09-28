package com.saibabui.main.presentation.viewmodels.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.domain.usecases.notification.GetNotificationsUseCase
import com.saibabui.main.domain.usecases.notification.MarkNotificationAsReadUseCase
import com.saibabui.main.domain.usecases.notification.MarkAllNotificationsAsReadUseCase
import com.saibabui.main.domain.usecases.notification.DeleteNotificationUseCase
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.NotificationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val markAllNotificationsAsReadUseCase: MarkAllNotificationsAsReadUseCase,
    private val deleteNotificationUseCase: DeleteNotificationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun loadNotifications(
        page: Int = 1,
        isRead: Boolean? = null,
        notificationType: String? = null
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getNotificationsUseCase(
                page = page,
                limit = 10,
                isRead = isRead,
                notificationType = notificationType
            ).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                    is ApiResponse.Success -> {
                        // Calculate unread count
                        val unreadCount = response.data.count { !it.isRead }
                        
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            notifications = response.data,
                            unreadCount = unreadCount,
                            currentPage = page
                        )
                    }
                }
            }
        }
    }

    fun markNotificationAsRead(notificationId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isMarkingAsRead = true, error = null)
            
            markNotificationAsReadUseCase(notificationId).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isMarkingAsRead = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isMarkingAsRead = true)
                    }
                    is ApiResponse.Success -> {
                        // Update the notification in the list
                        val updatedNotifications = _uiState.value.notifications.map { notification ->
                            if (notification.id == notificationId) {
                                notification.copy(isRead = true)
                            } else {
                                notification
                            }
                        }
                        
                        // Update unread count
                        val newUnreadCount = _uiState.value.unreadCount - 1
                        
                        _uiState.value = _uiState.value.copy(
                            isMarkingAsRead = false,
                            notifications = updatedNotifications,
                            unreadCount = newUnreadCount.coerceAtLeast(0),
                            successMessage = "Notification marked as read"
                        )
                    }
                }
            }
        }
    }

    fun markAllNotificationsAsRead() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isMarkingAsRead = true, error = null)
            
            markAllNotificationsAsReadUseCase().collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isMarkingAsRead = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isMarkingAsRead = true)
                    }
                    is ApiResponse.Success -> {
                        // Mark all notifications as read in the list
                        val updatedNotifications = _uiState.value.notifications.map { 
                            it.copy(isRead = true) 
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            isMarkingAsRead = false,
                            notifications = updatedNotifications,
                            unreadCount = 0,
                            successMessage = "All notifications marked as read"
                        )
                    }
                }
            }
        }
    }

    fun deleteNotification(notificationId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isDeleting = true, error = null)
            
            deleteNotificationUseCase(notificationId).collect { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isDeleting = false,
                            error = response.message
                        )
                    }
                    is ApiResponse.Loading -> {
                        _uiState.value = _uiState.value.copy(isDeleting = true)
                    }
                    is ApiResponse.Success -> {
                        // Remove the deleted notification from the list
                        val updatedNotifications = _uiState.value.notifications.filter { 
                            it.id != notificationId 
                        }
                        
                        // Update unread count if the deleted notification was unread
                        val deletedNotification = _uiState.value.notifications.find { 
                            it.id == notificationId 
                        }
                        val newUnreadCount = if (deletedNotification?.isRead == false) {
                            _uiState.value.unreadCount - 1
                        } else {
                            _uiState.value.unreadCount
                        }
                        
                        _uiState.value = _uiState.value.copy(
                            isDeleting = false,
                            notifications = updatedNotifications,
                            unreadCount = newUnreadCount.coerceAtLeast(0),
                            successMessage = "Notification deleted successfully"
                        )
                    }
                }
            }
        }
    }

    fun refreshNotifications() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            loadNotifications(
                _uiState.value.currentPage,
                null, // Reset filters when refreshing
                null
            )
            _uiState.value = _uiState.value.copy(isRefreshing = false)
        }
    }

    fun filterByType(notificationType: String?) {
        loadNotifications(1, null, notificationType)
    }

    fun filterByReadStatus(isRead: Boolean?) {
        loadNotifications(1, isRead, _uiState.value.selectedNotificationType)
    }

    fun showDeleteConfirmation(notification: NotificationResponse) {
        _uiState.value = _uiState.value.copy(
            showDeleteDialog = true,
            notificationToDelete = notification
        )
    }

    fun confirmDelete() {
        _uiState.value.notificationToDelete?.let { notification ->
            deleteNotification(notification.id)
        }
        _uiState.value = _uiState.value.copy(
            showDeleteDialog = false,
            notificationToDelete = null
        )
    }

    fun cancelDelete() {
        _uiState.value = _uiState.value.copy(
            showDeleteDialog = false,
            notificationToDelete = null
        )
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
}