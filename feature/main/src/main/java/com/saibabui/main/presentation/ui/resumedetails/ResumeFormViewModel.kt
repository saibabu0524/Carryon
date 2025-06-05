package com.saibabui.main.presentation.ui.resumedetails

import androidx.lifecycle.ViewModel
import com.saibabui.ui.CustomTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ResumeFormViewModel @Inject constructor() : ViewModel() {
    // Personal Info States
    val nameState = MutableStateFlow(CustomTextFieldState())
    val emailState = MutableStateFlow(CustomTextFieldState())
    val phoneState = MutableStateFlow(CustomTextFieldState())
    val addressState = MutableStateFlow(CustomTextFieldState())
    val linkedinState = MutableStateFlow(CustomTextFieldState())
    val summaryState = MutableStateFlow(CustomTextFieldState())

    // Experience States
    val jobTitleState = MutableStateFlow(CustomTextFieldState())
    val companyState = MutableStateFlow(CustomTextFieldState())
    val locationState = MutableStateFlow(CustomTextFieldState())
    val startDateState = MutableStateFlow(CustomTextFieldState())
    val endDateState = MutableStateFlow(CustomTextFieldState())
    val descriptionState = MutableStateFlow(CustomTextFieldState())

    // Education States
    val degreeState = MutableStateFlow(CustomTextFieldState())
    val institutionState = MutableStateFlow(CustomTextFieldState())
    val graduationYearState = MutableStateFlow(CustomTextFieldState())
    val gpaState = MutableStateFlow(CustomTextFieldState())

    // Skills State
    val skillsState = MutableStateFlow(CustomTextFieldState())

    fun updateName(value: String) {
        nameState.value = nameState.value.copy(
            value = value,
            error = if (value.isBlank()) "Name is required" else ""
        )
    }

    fun updateEmail(value: String) {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        nameState.value = emailState.value.copy(
            value = value,
            error = when {
                value.isBlank() -> "Email is required"
                !value.matches(emailPattern.toRegex()) -> "Invalid email format"
                else -> ""
            }
        )
    }

    fun updatePhone(value: String) {
        phoneState.value = phoneState.value.copy(
            value = value,
            error = if (value.isBlank()) "Phone number is required" else ""
        )
    }

    fun updateAddress(value: String) {
        addressState.value = addressState.value.copy(value = value, error = "")
    }

    fun updateLinkedin(value: String) {
        linkedinState.value = linkedinState.value.copy(value = value, error = "")
    }

    fun updateSummary(value: String) {
        summaryState.value = summaryState.value.copy(value = value, error = "")
    }

    // Add similar update functions for other fields...
}