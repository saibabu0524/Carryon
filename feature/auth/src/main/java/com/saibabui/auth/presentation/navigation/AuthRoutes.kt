package com.saibabui.auth.presentation.navigation


sealed class AuthScreen(val route: String) {
    data object Login : AuthScreen("login")
    data object SignUp : AuthScreen("signup")
    data object Dashboard : AuthScreen("dashboard")
    data object VerifyMobileNumber : AuthScreen("verify_mobile_number")
}

sealed class Home(val route: String) {

}


object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
}
