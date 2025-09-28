package com.saibabui.auth.presentation.ui.resetpassword

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
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val password by viewModel.password.collectAsStateWithLifecycle()
    val confirmPassword by viewModel.confirmPassword.collectAsStateWithLifecycle()
    val resetPasswordState by viewModel.resetPasswordState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = resetPasswordState) {
        when (resetPasswordState) {
            is UiState.Success<*> -> {
                Toast.makeText(
                    context,
                    "Password reset successfully",
                    Toast.LENGTH_LONG
                ).show()
                navController.popBackStack() // Navigate back to login after successful reset
            }
            is UiState.Error -> {
                Toast.makeText(
                    context,
                    "Error: ${(resetPasswordState as UiState.Error).message}",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBarComposable(arrowBackIcon = com.saibabui.ui.R.drawable.back_icon) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        ResetPasswordScreenBody(
            paddingValues = paddingValues,
            navController = navController,
            viewModel = viewModel,
            password = password,
            confirmPassword = confirmPassword,
            resetPasswordState = resetPasswordState
        )
    }
}

@Composable
fun ResetPasswordScreenBody(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    navController: NavController,
    viewModel: ResetPasswordViewModel,
    password: com.saibabui.ui.CustomTextFieldState,
    confirmPassword: com.saibabui.ui.CustomTextFieldState,
    resetPasswordState: UiState<*>
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
                HeadingText("Reset Password")
                Text(
                    text = "Create a new password for your account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                CustomeTextField(
                    supportingText = stringResource(R.string.new_password),
                    onValueChange = { viewModel.setPassword(it) },
                    internalState = viewModel.password,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )

                CustomeTextField(
                    supportingText = stringResource(R.string.confirm_password),
                    onValueChange = { viewModel.setConfirmPassword(it) },
                    internalState = viewModel.confirmPassword,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )

                val isFormValid = password.isValid && confirmPassword.isValid
                PrimaryButton(
                    buttonText = "Reset Password",
                    isEnabled = isFormValid
                ) {
                    viewModel.submit()
                }
            }
        }

        when (resetPasswordState) {
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
                        text = "Error: ${(resetPasswordState as UiState.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {}
        }
    }
}