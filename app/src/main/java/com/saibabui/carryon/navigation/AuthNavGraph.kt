package com.saibabui.carryon.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saibabui.auth.presentation.ui.dashboard.DashBoardScreen
import com.saibabui.auth.presentation.ui.dashboard.DashboardNavigation
import com.saibabui.auth.presentation.ui.login.LoginRoute
import com.saibabui.auth.presentation.ui.login.LoginScreen
import com.saibabui.auth.presentation.ui.verify.VerifyMobileNumberScreen
import com.saibabui.auth.presentation.ui.verify.VerifyScreenNavigation


fun NavGraphBuilder.authNavGraph(navController: NavHostController, navigateToHome: () -> Unit) {
    navigation<Authentication>(
        startDestination = DashboardNavigation
    ) {
        composable<LoginRoute> {
            LoginScreen(
                navController
            )
        }
        composable<DashboardNavigation> {
            DashBoardScreen(navController = navController)
        }
        composable<VerifyScreenNavigation> {
            VerifyMobileNumberScreen(navController, navigateToHome)
        }
    }
}