package com.saibabui.auth.presentation.ui.changepassword

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
object ChangePasswordRoute

fun NavController.navigateToChangePassword(navOptions: NavOptions? = null) =
    navigate(route = ChangePasswordRoute, navOptions)