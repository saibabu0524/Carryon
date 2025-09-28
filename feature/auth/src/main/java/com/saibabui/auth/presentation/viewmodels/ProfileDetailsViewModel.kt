package com.saibabui.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.auth.utils.UiState
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.UserProfileDetails
import com.saibabui.network.home.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailsViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _profileDetailsState = MutableStateFlow<UiState<UserProfileDetails>>(UiState.Idle)
    val profileDetailsState: StateFlow<UiState<UserProfileDetails>> = _profileDetailsState.asStateFlow()

    fun getProfileDetails() {
        viewModelScope.launch {
            homeRepository.getProfileDetails().collectLatest { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        _profileDetailsState.value = UiState.Error(response.message)
                    }
                    is ApiResponse.Loading -> {
                        _profileDetailsState.value = UiState.Loading
                    }
                    is ApiResponse.Success -> {
                        _profileDetailsState.value = UiState.Success(response.data)
                    }
                }
            }
        }
    }
}