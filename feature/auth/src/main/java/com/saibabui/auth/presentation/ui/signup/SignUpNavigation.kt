package com.saibabui.auth.presentation.ui.signup


import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.saibabui.auth.presentation.ui.login.LoginRoute
import kotlinx.serialization.Serializable


@Serializable
object SignUpRoute

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) =
    navigate(route = SignUpRoute, navOptions)
