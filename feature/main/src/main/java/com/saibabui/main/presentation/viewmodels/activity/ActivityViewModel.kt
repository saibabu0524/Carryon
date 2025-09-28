package com.saibabui.main.presentation.viewmodels.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.domain.usecases.activity.GetActivityHistoryUseCase
import com.saibabui.main.domain.usecases.activity.GetDashboardStatsUseCase
import com.saibabui.main.domain.usecases.activity.GetResumeAnalyticsUseCase
import com.saibabui.network.auth.model.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivityHistoryUseCase: GetActivityHistoryUseCase,
    private val getDashboardStatsUseCase: GetDashboardStatsUseCase,
    private val getResumeAnalyticsUseCase: GetResumeAnalyticsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    init {
        loadActivityHistory()
        loadDashboardStats()
    }

    fun loadActivityHistory(page: Int = 1, actionType: String? = null) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getActivityHistoryUseCase(
                page = page,
                limit = 10,
                actionType = actionType
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
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            activities = response.data,
                            currentPage = page,
                            selectedActionType = actionType
                        )
                    }
                }
            }
        }
    }

    fun loadDashboardStats() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getDashboardStatsUseCase().collect { response ->
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
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            dashboardStats = response.data
                        )
                    }
                }
            }
        }
    }

    fun loadResumeAnalytics(resumeId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getResumeAnalyticsUseCase(resumeId).collect { response ->
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
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            resumeAnalytics = response.data
                        )
                    }
                }
            }
        }
    }

    fun refreshAll() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            loadActivityHistory(_uiState.value.currentPage, _uiState.value.selectedActionType)
            loadDashboardStats()
            _uiState.value = _uiState.value.copy(isRefreshing = false)
        }
    }

    fun filterByActionType(actionType: String?) {
        loadActivityHistory(1, actionType)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun clearSuccessMessage() {
        _uiState.value = _uiState.value.copy(successMessage = null)
    }
}