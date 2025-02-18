package com.saibabui.auth.presentation.ui.login


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable


@Serializable
object LoginRoute

fun NavController.navigateToLogin(navOptions: NavOptions) =
    navigate(route = LoginRoute, navOptions)

fun NavGraphBuilder.loginScreen() {
    composable<LoginRoute> {
        LoginRoute()
    }
}