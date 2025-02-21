package com.saibabui.main.navigation

sealed class Home(val route: String) {
    data object HomeScreen : Home("homeScreen")
    data object ProfileScreen : Home("profileScreen")
    data object Activity : Home("activity")
    data object Transactions : Home("transactions")
}
