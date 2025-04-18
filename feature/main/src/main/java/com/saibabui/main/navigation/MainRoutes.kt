package com.saibabui.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable


@Serializable
sealed class Home(val route: String) {
    @Serializable
    object HomeScreenDestination : Home("home_screen")

    @Serializable
    object ActivityDestination : Home("activity")

    @Serializable
    object TransactionsDestination : Home("transactions")

    @Serializable
    object ProfileScreenDestination : Home("profile_screen")
}


@Serializable
object ResumePreviewScreen


fun NavController.navigateToResumeScreen(navOptions: NavOptions? = null) =
    navigate(route = ResumePreviewScreen, navOptions)