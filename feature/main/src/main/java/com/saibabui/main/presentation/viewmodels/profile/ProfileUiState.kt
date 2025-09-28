package com.saibabui.main.presentation.viewmodels.profile

import com.saibabui.network.home.model.ProfileResponse

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: ProfileResponse? = null,
    val error: String? = null,
    val successMessage: String? = null,
    val isEditing: Boolean = false,
    val isUploadingAvatar: Boolean = false,
    val avatarUploadProgress: Float = 0f
)