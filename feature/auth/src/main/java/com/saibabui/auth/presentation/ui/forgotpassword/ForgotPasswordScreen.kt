package com.saibabui.auth.presentation.ui.forgotpassword

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
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val forgotPasswordState by viewModel.forgotPasswordState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = forgotPasswordState) {
        when (forgotPasswordState) {
            is UiState.Success<*> -> {
                Toast.makeText(
                    context,
                    "Password reset link sent to your email",
                    Toast.LENGTH_LONG
                ).show()
                navController.popBackStack()
            }
            is UiState.Error -> {
                Toast.makeText(
                    context,
                    "Error: ${(forgotPasswordState as UiState.Error).message}",
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
        ForgotPasswordScreenBody(
            paddingValues = paddingValues,
            navController = navController,
            viewModel = viewModel,
            email = email,
            forgotPasswordState = forgotPasswordState
        )
    }
}

@Composable
fun ForgotPasswordScreenBody(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    navController: NavController,
    viewModel: ForgotPasswordViewModel,
    email: com.saibabui.ui.CustomTextFieldState,
    forgotPasswordState: UiState<*>
) {
    val context = LocalContext.current

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
                HeadingText("Forgot Password")
                Text(
                    text = "Enter your email address and we'll send you a link to reset your password",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                CustomeTextField(
                    supportingText = stringResource(R.string.email),
                    onValueChange = { viewModel.setEmail(it) },
                    internalState = viewModel.email,
                    keyboardType = KeyboardType.Email
                )

                PrimaryButton(
                    buttonText = "Send Reset Link",
                    isEnabled = email.isValid
                ) {
                    viewModel.submit()
                }
            }
        }

        when (forgotPasswordState) {
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
                        text = "Error: ${(forgotPasswordState as UiState.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {}
        }
    }
}