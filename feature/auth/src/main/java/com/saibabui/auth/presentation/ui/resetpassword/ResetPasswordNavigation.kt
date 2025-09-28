package com.saibabui.auth.presentation.ui.resetpassword

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
object ResetPasswordRoute

fun NavController.navigateToResetPassword(navOptions: NavOptions? = null) =
    navigate(route = ResetPasswordRoute, navOptions)