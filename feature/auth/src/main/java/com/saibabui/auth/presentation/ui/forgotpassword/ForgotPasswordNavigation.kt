package com.saibabui.auth.presentation.ui.forgotpassword

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
object ForgotPasswordRoute

fun NavController.navigateToForgotPassword(navOptions: NavOptions? = null) =
    navigate(route = ForgotPasswordRoute, navOptions)