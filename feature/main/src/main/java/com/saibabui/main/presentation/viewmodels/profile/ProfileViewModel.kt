package com.saibabui.main.presentation.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saibabui.main.domain.usecases.profile.GetProfileDetailsUseCase
import com.saibabui.main.domain.usecases.profile.UpdateProfileUseCase
import com.saibabui.main.domain.usecases.profile.UploadAvatarUseCase
import com.saibabui.main.domain.usecases.profile.DeleteAvatarUseCase
import com.saibabui.network.auth.model.ApiResponse
import com.saibabui.network.home.model.ProfileUpdateRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileDetailsUseCase: GetProfileDetailsUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadAvatarUseCase: UploadAvatarUseCase,
    private val deleteAvatarUseCase: DeleteAvatarUseCase
) : ViewModel() {

//    private val _uiState = MutableStateFlow(ProfileUiState())
//    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
//
//    init {
//        loadProfile()
//    }
//
//    fun loadProfile() {
//        viewModelScope.launch {
//            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
//
//            when (val response = getProfileDetailsUseCase()) {
//                is ApiResponse.Error -> {
//                    _uiState.value = _uiState.value.copy(
//                        isLoading = false,
//                        error = response.message
//                    )
//                }
//
//                is ApiResponse.Loading -> {
//                    _uiState.value = _uiState.value.copy(isLoading = true)
//                }
//
//                is ApiResponse.Success -> {
//                    _uiState.value = _uiState.value.copy(
//                        isLoading = false,
//                        profile = response.data
//                    )
//                }
//            }
//        }
//    }
//
//    fun updateProfile(request: ProfileUpdateRequest) {
//        viewModelScope.launch {
//            _uiState.value =
//                _uiState.value.copy(isLoading = true, error = null, successMessage = null)
//
//            when (val response = updateProfileUseCase(request)) {
//                is ApiResponse.Error -> {
//                    _uiState.value = _uiState.value.copy(
//                        isLoading = false,
//                        error = response.message
//                    )
//                }
//
//                is ApiResponse.Loading -> {
//                    _uiState.value = _uiState.value.copy(isLoading = true)
//                }
//
//                is ApiResponse.Success -> {
//                    _uiState.value = _uiState.value.copy(
//                        isLoading = false,
//                        profile = response.data,
//                        successMessage = "Profile updated successfully",
//                        isEditing = false
//                    )
//                }
//            }
//        }
//    }
//
//    fun uploadAvatar(file: File) {
//        viewModelScope.launch {
//            _uiState.value = _uiState.value.copy(
//                isUploadingAvatar = true,
//                error = null,
//                successMessage = null
//            )
//
//            uploadAvatarUseCase(file).collect { response ->
//                when (response) {
//                    is ApiResponse.Error -> {
//                        _uiState.value = _uiState.value.copy(
//                            isUploadingAvatar = false,
//                            error = response.message
//                        )
//                    }
//
//                    is ApiResponse.Loading -> {
//                        _uiState.value = _uiState.value.copy(isUploadingAvatar = true)
//                    }
//
//                    is ApiResponse.Success<*> -> {
//                        val data = response.data as? Map<String, Any>
//                        _uiState.value = _uiState.value.copy(
//                            isUploadingAvatar = false,
//                            profile = _uiState.value.profile?.copy(
//                                avatarUrl = data?.get("avatar_url") as? String
//                            ),
//                            successMessage = "Avatar uploaded successfully"
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    fun deleteAvatar() {
//        viewModelScope.launch {
//            _uiState.value =
//                _uiState.value.copy(isLoading = true, error = null, successMessage = null)
//
//            val response = deleteAvatarUseCase()
//            when (response) {
//                is ApiResponse.Error -> {
//                    _uiState.value = _uiState.value.copy(
//                        isLoading = false,
//                        error = response.message
//                    )
//                }
//
//                is ApiResponse.Loading -> {
//                    _uiState.value = _uiState.value.copy(isLoading = true)
//                }
//
//                is ApiResponse.Success -> {
//                    _uiState.value = _uiState.value.copy(
//                        isLoading = false,
//                        profile = _uiState.value.profile?.copy(avatarUrl = null),
//                        successMessage = "Avatar deleted successfully"
//                    )
//                }
//            }
//        }
//    }
}