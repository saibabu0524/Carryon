package com.saibabui.auth.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saibabui.auth.presentation.ui.dashboard.DashBoardScreen
import com.saibabui.auth.presentation.ui.login.LoginRoute
import com.saibabui.auth.presentation.ui.verify.VerifyMobileNumberScreen


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Dashboard.route
    ) {
        composable(AuthScreen.Login.route) {
            LoginRoute(
                navController
            )
        }
        composable(AuthScreen.SignUp.route) {
        }
        composable(AuthScreen.Dashboard.route) {
            DashBoardScreen(navController = navController)
        }
        composable(AuthScreen.VerifyMobileNumber.route) {
            VerifyMobileNumberScreen(navController)
        }
    }
}