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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.saibabui.auth.R
import com.saibabui.auth.presentation.ui.verify.navigateToVerifyScreen
import com.saibabui.auth.utils.TopAppBarComposable
import com.saibabui.auth.utils.UiState
import com.saibabui.network.auth.model.LoginResponse
import com.saibabui.ui.CustomeTextField
import com.saibabui.ui.HeadingText
import com.saibabui.ui.PrimaryButton

@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewmodel = hiltViewModel()

    // Use Scaffold for the basic layout with a TopAppBar.
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
            loginViewmodel = loginViewModel
        )
    }
}

@Composable
fun LoginScreenBody(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    navController: NavController,
    loginViewmodel: LoginViewmodel
) {
    // Observe the login state from the ViewModel.
    val loginState by loginViewmodel.loginState.collectAsStateWithLifecycle()
    val mobileNumber by loginViewmodel.mobileNumber.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Trigger navigation as a side effect when login is successful.
    LaunchedEffect(key1 = loginState) {
        if (loginState is UiState.Success) {
            Toast.makeText(
                context,
                (loginState as UiState.Success<LoginResponse>).data.message,
                Toast.LENGTH_SHORT
            ).show()
            navController.navigateToVerifyScreen()
        }
    }

    // Use a Box to overlay loading/error indicators on top of your form.
    Box(modifier = Modifier.fillMaxSize()) {
        // The main content (login form)
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
                HeadingText(stringResource(R.string.welcome_back_sign_in_using_mobile_number_to_continue_us))
                CustomeTextField(
                    supportingText = stringResource(R.string.your_mobile_number),
                    onValueChange = { newValue ->
                        loginViewmodel.validate(SignUpFormEvents.MobileNumberChangeEvent(newValue))
                    },
                    internalState = loginViewmodel.mobileNumber,
                    keyboardType = KeyboardType.Number
                )
                PrimaryButton(
                    buttonText = stringResource(id = R.string.continue_),
                    isEnabled = mobileNumber.isValid
                ) {
                    loginViewmodel.validate(SignUpFormEvents.SignUpButtonEvent)
                }
            }
        }

        // Overlay:   z indicator and error message.
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
