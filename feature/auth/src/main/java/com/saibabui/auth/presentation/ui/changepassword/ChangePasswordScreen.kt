package com.saibabui.auth.presentation.ui.changepassword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.saibabui.auth.R
import com.saibabui.auth.utils.TopAppBarComposable
import com.saibabui.auth.utils.UiState
import com.saibabui.ui.CustomeTextField
import com.saibabui.ui.HeadingText
import com.saibabui.ui.PrimaryButton

@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val oldPassword by viewModel.oldPassword.collectAsStateWithLifecycle()
    val newPassword by viewModel.newPassword.collectAsStateWithLifecycle()
    val confirmNewPassword by viewModel.confirmNewPassword.collectAsStateWithLifecycle()
    val changePasswordState by viewModel.changePasswordState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = changePasswordState) {
        when (changePasswordState) {
            is UiState.Success<*> -> {
                Toast.makeText(
                    context,
                    "Password changed successfully",
                    Toast.LENGTH_LONG
                ).show()
                navController.popBackStack() // Navigate back after successful change
            }
            is UiState.Error -> {
                Toast.makeText(
                    context,
                    "Error: ${(changePasswordState as UiState.Error).message}",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBarComposable(arrowBackIcon = R.drawable.back_icon) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        ChangePasswordScreenBody(
            paddingValues = paddingValues,
            navController = navController,
            viewModel = viewModel,
            oldPassword = oldPassword,
            newPassword = newPassword,
            confirmNewPassword = confirmNewPassword,
            changePasswordState = changePasswordState
        )
    }
}

@Composable
fun ChangePasswordScreenBody(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    navController: NavController,
    viewModel: ChangePasswordViewModel,
    oldPassword: com.saibabui.ui.CustomTextFieldState,
    newPassword: com.saibabui.ui.CustomTextFieldState,
    confirmNewPassword: com.saibabui.ui.CustomTextFieldState,
    changePasswordState: UiState<*>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .imePadding()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp)
            ) {
                HeadingText("Change Password")
                Text(
                    text = "Enter your current password and create a new password",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                CustomeTextField(
                    supportingText = stringResource(R.string.current_password),
                    onValueChange = { viewModel.setOldPassword(it) },
                    internalState = viewModel.oldPassword,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )

                CustomeTextField(
                    supportingText = stringResource(R.string.new_password),
                    onValueChange = { viewModel.setNewPassword(it) },
                    internalState = viewModel.newPassword,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )

                CustomeTextField(
                    supportingText = stringResource(R.string.confirm_password),
                    onValueChange = { viewModel.setConfirmNewPassword(it) },
                    internalState = viewModel.confirmNewPassword,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )

                val isFormValid = oldPassword.isValid && newPassword.isValid && confirmNewPassword.isValid
                PrimaryButton(
                    buttonText = "Change Password",
                    isEnabled = isFormValid
                ) {
                    viewModel.submit()
                }
            }
        }

        when (changePasswordState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "Error: ${(changePasswordState as UiState.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {}
        }
    }
}