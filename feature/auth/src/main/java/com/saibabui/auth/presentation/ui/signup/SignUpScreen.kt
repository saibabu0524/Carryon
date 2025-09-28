package com.saibabui.auth.presentation.ui.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.saibabui.auth.presentation.ui.forgotpassword.navigateToForgotPassword
import com.saibabui.auth.utils.TopAppBarComposable
import com.saibabui.auth.utils.UiState
import com.saibabui.ui.CustomeTextField
import com.saibabui.ui.HeadingText
import com.saibabui.ui.PrimaryButton
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.saibabui.auth.presentation.ui.dashboard.DividerOr
import com.saibabui.auth.presentation.ui.login.navigateToLogin
import com.saibabui.ui.SecondaryButton

@Composable
fun SignUpScreen(
    navController: NavController,
    navigateToHome: () -> Unit = {}
) {
    val signUpViewModel = hiltViewModel<SignUpViewModel>()

    Scaffold(
        topBar = {
            TopAppBarComposable(arrowBackIcon = R.drawable.back_icon) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        SignUpScreenBody(
            paddingValues = paddingValues,
            navController = navController,
            signUpViewModel = signUpViewModel,
            navigateToHome= navigateToHome
        )
    }
}

@Composable
fun SignUpScreenBody(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    navController: NavController,
    signUpViewModel: SignUpViewModel,
    navigateToHome: () -> Unit = {}
) {
    val firstName by signUpViewModel.firstName.collectAsStateWithLifecycle()
    val lastName by signUpViewModel.lastName.collectAsStateWithLifecycle()
    val email by signUpViewModel.email.collectAsStateWithLifecycle()
    val password by signUpViewModel.password.collectAsStateWithLifecycle()
    val confirmPassword by signUpViewModel.confirmPassword.collectAsStateWithLifecycle()
    val signUpState by signUpViewModel.signUpState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = signUpState) {
        if (signUpState is UiState.Success<*>) {
            val message = try {
                (signUpState as? UiState.Success<SignUpResponse>)
                    ?.data
                    ?.message ?: "Success"
            } catch (e: Exception) {
                "Success"
            }

            Toast.makeText(
                context,
                message,
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
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                HeadingText(stringResource(R.string.create_account_sign_up_using_mobile_number_to_continue_us))
                HeadingText(stringResource(R.string.sign_up))
                CustomeTextField(
                    supportingText = stringResource(R.string.first_name),
                    onValueChange = { signUpViewModel.onEvent(SignUpFormEvent.FirstNameChanged(it)) },
                    internalState = signUpViewModel.firstName,
                    keyboardType = KeyboardType.Text
                )
                CustomeTextField(
                    supportingText = stringResource(R.string.last_name),
                    onValueChange = { signUpViewModel.onEvent(SignUpFormEvent.LastNameChanged(it)) },
                    internalState = signUpViewModel.lastName,
                    keyboardType = KeyboardType.Text
                )
                CustomeTextField(
                    supportingText = stringResource(R.string.email),
                    onValueChange = { signUpViewModel.onEvent(SignUpFormEvent.EmailChanged(it)) },
                    internalState = signUpViewModel.email,
                    keyboardType = KeyboardType.Email
                )
                CustomeTextField(
                    supportingText = stringResource(R.string.password),
                    onValueChange = { signUpViewModel.onEvent(SignUpFormEvent.PasswordChanged(it)) },
                    internalState = signUpViewModel.password,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )
                CustomeTextField(
                    supportingText = stringResource(R.string.confirm_password),
                    onValueChange = {
                        signUpViewModel.onEvent(
                            SignUpFormEvent.ConfirmPasswordChanged(
                                it
                            )
                        )
                    },
                    internalState = signUpViewModel.confirmPassword,
                    keyboardType = KeyboardType.Password,
                    visualTransformation = PasswordVisualTransformation()
                )
                val isFormValid =
                    firstName.isValid && lastName.isValid && email.isValid && password.isValid && confirmPassword.isValid
                PrimaryButton(
                    buttonText = stringResource(id = R.string.sign_up),
                    isEnabled = isFormValid
                ) {
                    signUpViewModel.onEvent(SignUpFormEvent.Submit)
                }
                Row {
                    DividerOr(
                        divideString = stringResource(id = R.string.or)
                    )
                }
                SecondaryButton(
                    buttonText = stringResource(id = R.string.already_have_an_account),
                    onClick = {
                        navController.navigateToLogin()
                    }
                )
            }
        }

        when (signUpState) {
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
                        text = "Error: ${(signUpState as UiState.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {}
        }
    }
}