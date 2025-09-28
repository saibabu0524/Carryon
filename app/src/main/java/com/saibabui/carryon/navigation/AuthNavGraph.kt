package com.saibabui.carryon.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saibabui.auth.presentation.ui.changepassword.ChangePasswordScreen
import com.saibabui.auth.presentation.ui.changepassword.ChangePasswordRoute
import com.saibabui.auth.presentation.ui.dashboard.DashBoardScreen
import com.saibabui.auth.presentation.ui.dashboard.DashboardNavigation
import com.saibabui.auth.presentation.ui.forgotpassword.ForgotPasswordRoute
import com.saibabui.auth.presentation.ui.forgotpassword.ForgotPasswordScreen
import com.saibabui.auth.presentation.ui.login.LoginRoute
import com.saibabui.auth.presentation.ui.login.LoginScreen
import com.saibabui.auth.presentation.ui.resetpassword.ResetPasswordRoute
import com.saibabui.auth.presentation.ui.resetpassword.ResetPasswordScreen
import com.saibabui.auth.presentation.ui.signup.SignUpRoute
import com.saibabui.auth.presentation.ui.signup.SignUpScreen


fun NavGraphBuilder.authNavGraph(navController: NavHostController, navigateToHome: () -> Unit) {
    navigation<Authentication>(
        startDestination = DashboardNavigation
    ) {
        composable<LoginRoute> {
            LoginScreen(
                navController,navigateToHome
            )
        }
        composable<SignUpRoute> {
            SignUpScreen(
                navController,navigateToHome
            )
        }
        composable<DashboardNavigation> {
            DashBoardScreen(navController = navController)
        }
        composable<ForgotPasswordRoute> {
            ForgotPasswordScreen(navController = navController)
        }
        composable<ResetPasswordRoute> {
            ResetPasswordScreen(navController = navController)
        }
        composable<ChangePasswordRoute> {
            ChangePasswordScreen(navController = navController)
        }
    }
}