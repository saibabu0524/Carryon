package com.saibabui.auth.presentation.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saibabui.auth.R
import com.saibabui.auth.presentation.ui.login.PrimaryButton
import com.saibabui.auth.presentation.ui.login.navigateToLogin


@Composable
fun DashBoardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

//    val homeViewModel = com.saibabui.carryon.HomeViewModel()

//    LaunchedEffect(key1 = Unit) {
//        val authService = RetrofitClient.authService
//        val repository = AuthRepositoryImpl(authService)
//        repository.testServer().collectLatest{value->
//            println("api hit step-1")
//            println(value)
//        }
//        println("api hit step-1")
//    }


    Column(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DashBoardHeading(heading = stringResource(R.string.resume_builder_message))
        HeadingShortDescription(shortDescription = stringResource(R.string.resume_builder_short_description))
        PrimaryButton(stringResource(R.string.sign_up_with_mobile_number)) {
            navController.navigateToLogin()
        }
    }
}


@Preview
@Composable
private fun DashBoardPreview() {
    MaterialTheme {
        Surface {
            DashBoardScreen(navController = NavController(LocalContext.current))
        }
    }
}


