package com.saibabui.auth.presentation.ui.verify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saibabui.auth.R
import com.saibabui.auth.presentation.ui.login.PrimaryButton
import com.saibabui.auth.presentation.ui.login.CustomTextFieldState
import com.saibabui.auth.presentation.ui.login.CustomeTextField
import com.saibabui.auth.presentation.ui.login.HeadingText
import com.saibabui.auth.presentation.ui.login.LoginViewmodel
import com.saibabui.auth.presentation.ui.login.SecondaryButton
import com.saibabui.auth.utils.TopAppBarComposable
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun VerifyMobileNumberScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBarComposable(arrowBackIcon = R.drawable.back_icon) {
                navController.popBackStack()
            }
        }
    ) { paddingValues ->
        VerifyMobileNumberScreen(
            paddingValues = paddingValues,
            onVerifyButtonClick = {
                //navController.navigateToHome()
            },
            onResendOtpClick = {},
            onBackClick = {}
        )
    }
}


@Composable
fun VerifyMobileNumberScreen(
    paddingValues: PaddingValues = PaddingValues(20.dp),
    onVerifyButtonClick: () -> Unit,
    onResendOtpClick: () -> Unit,
    onBackClick: () -> Unit
) {
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
//            loginViewmodel.validate(
//                SignUpFormEvents.EmailChangedEvent(it)
//            )
                }, internalState = MutableStateFlow(CustomTextFieldState("", "", false)),
                keyboardType = KeyboardType.Number
            )
            PrimaryButton(buttonText = stringResource(id = R.string.verify_mobile_number)) {
                onVerifyButtonClick()
//        loginViewmodel.validate(SignUpFormEvents.SignUpButtonEvent)
            }
            SecondaryButton(buttonText = stringResource(id = R.string.resend_otp)) {
//        loginViewmodel.validate(SignUpFormEvents.SignUpButtonEvent)
            }
        }
    }
}
