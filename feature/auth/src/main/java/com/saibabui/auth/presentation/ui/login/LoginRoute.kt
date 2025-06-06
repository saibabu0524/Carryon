package com.saibabui.auth.presentation.ui.login

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
import com.saibabui.auth.presentation.ui.signup.navigateToSignUp
import com.saibabui.auth.utils.TopAppBarComposable
import com.saibabui.auth.utils.UiState
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.ui.CustomeTextField
import com.saibabui.ui.HeadingText
import com.saibabui.ui.PrimaryButton
import com.saibabui.ui.SecondaryButton

@Composable
fun LoginScreen(navController: NavController, navigateToHome: () -> Unit) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopAppBarComposable(arrowBackIcon = R.drawable.back_icon) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        LoginScreenBody(
            paddingValues = paddingValues,
            navController = navController,
            loginViewModel = loginViewModel,
            navigateToHome = navigateToHome
        )
    }
}

@Composable
fun LoginScreenBody(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    navController: NavController,
    loginViewModel: LoginViewModel,
    navigateToHome: () -> Unit = {}
) {
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()
    val email by loginViewModel.email.collectAsStateWithLifecycle()
    val password by loginViewModel.password.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = loginState) {
        if (loginState is UiState.Success) {
            Toast.makeText(
                context,
                (loginState as UiState.Success<LoginResponse>).data.message,
                Toast.LENGTH_SHORT
            ).show()
            navigateToHome()
        }
    }

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
                HeadingText(stringResource(R.string.welcome_back_sign_in_using_email_and_password))
                CustomeTextField(
                    supportingText = stringResource(R.string.email),
                    onValueChange = { newValue ->
                        loginViewModel.validate(LoginFormEvent.EmailChanged(newValue))
                    },
                    internalState = loginViewModel.email,
                    keyboardType = KeyboardType.Email
                )
                CustomeTextField(
                    supportingText = stringResource(R.string.password),
                    onValueChange = { newValue ->
                        loginViewModel.validate(LoginFormEvent.PasswordChanged(newValue))
                    },
                    internalState = loginViewModel.password,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )
                PrimaryButton(
                    buttonText = stringResource(id = R.string.login),
                    isEnabled = email.isValid && password.isValid
                ) {
                    loginViewModel.validate(LoginFormEvent.Submit)
                }
                SecondaryButton(
                    buttonText = stringResource(id = R.string.sign_up_with_email)
                ) {
                    navController.navigateToSignUp()
                }
            }
        }

        when (loginState) {
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
                        text = "Error: ${(loginState as UiState.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {}
        }
    }
}