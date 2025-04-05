package com.saibabui.auth.presentation.ui.verify

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saibabui.auth.R
import com.saibabui.auth.presentation.ui.login.CustomeTextField
import com.saibabui.auth.presentation.ui.login.HeadingText
import com.saibabui.auth.presentation.ui.login.LoginViewmodel
import com.saibabui.auth.presentation.ui.login.PrimaryButton
import com.saibabui.auth.presentation.ui.login.SecondaryButton
import com.saibabui.auth.presentation.ui.login.SignUpFormEvents
import com.saibabui.auth.utils.TopAppBarComposable
import com.saibabui.auth.utils.UiState
import com.saibabui.network.auth.model.VerifyOTPResponse

@Composable
fun VerifyMobileNumberScreen(
    navController: NavController,
    navigateToHome: () -> Unit
) {
    val loginViewModel: LoginViewmodel = hiltViewModel()
    Scaffold(
        topBar = {
            TopAppBarComposable(arrowBackIcon = R.drawable.back_icon) {
                navController.navigateUp()
            }
        }
    ) { paddingValues ->
        VerifyMobileNumberScreen(
            paddingValues = paddingValues,
            loginViewmodel = loginViewModel,
            onVerifyButtonClick = {
                loginViewModel.validate(SignUpFormEvents.OTPContinueButtonEvent)
            },
            onResendOtpClick = {
                loginViewModel.validate(SignUpFormEvents.SignUpButtonEvent)
            },
            onBackClick = {
                navController.navigateUp()
            }
        ) {
            navigateToHome()
        }
    }
}


@Composable
fun VerifyMobileNumberScreen(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    loginViewmodel: LoginViewmodel,
    onVerifyButtonClick: () -> Unit,
    onResendOtpClick: () -> Unit,
    onBackClick: () -> Unit,
    navigateToHome: () -> Unit
) {

    val uiState by loginViewmodel.otpScreenState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState) {
        if (uiState is UiState.Success) {
            Toast.makeText(
                context,
                (uiState as UiState.Success<VerifyOTPResponse>).data.message,
                Toast.LENGTH_SHORT
            ).show()
            navigateToHome()
        }
    }

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .imePadding()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp)
            ) {
                HeadingText("Please enter the OTP sent to your mobile number")
                CustomeTextField(
                    supportingText = stringResource(R.string.enter_otp), onValueChange = {
                        loginViewmodel.validate(
                            SignUpFormEvents.MobileOTPChangeEvent(it)
                        )
                    }, internalState = loginViewmodel.mobileOTP,
                    keyboardType = KeyboardType.Number
                )
                PrimaryButton(buttonText = stringResource(id = R.string.verify_mobile_number)) {
                    onVerifyButtonClick()
                }
                SecondaryButton(buttonText = stringResource(id = R.string.resend_otp)) {
                    //      loginViewmodel.validate(SignUpFormEvents.SignUpButtonEvent)
                }
            }
        }
        when (uiState) {
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
                        text = "Error: ${(uiState as UiState.Error).message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {}
        }
    }
}
