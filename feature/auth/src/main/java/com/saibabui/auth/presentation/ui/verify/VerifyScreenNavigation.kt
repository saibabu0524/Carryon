package com.saibabui.auth.presentation.ui.verify


import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.saibabui.auth.presentation.ui.dashboard.DashboardNavigation
import kotlinx.serialization.Serializable

@Serializable
object VerifyScreenNavigation

fun NavController.navigateToVerifyScreen(navOptions: NavOptions? = null) =
    navigate(route = VerifyScreenNavigation, navOptions)