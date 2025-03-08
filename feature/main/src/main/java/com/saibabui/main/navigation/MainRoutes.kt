package com.saibabui.main.navigation

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
