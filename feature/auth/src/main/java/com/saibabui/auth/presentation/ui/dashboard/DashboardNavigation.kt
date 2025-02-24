package com.saibabui.auth.presentation.ui.dashboard

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
object DashboardNavigation

fun NavController.navigateToDashboard(navOptions: DashboardNavigation? = null) =
    navigate(route = DashboardNavigation)