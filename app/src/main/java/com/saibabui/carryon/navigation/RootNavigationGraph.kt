package com.saibabui.carryon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saibabui.main.navigation.Home
import com.saibabui.carryon.navigation.Home as HomeScreen

@Composable
fun RootNavigationGraph(navController: NavHostController,navigateToHome : () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ) {
        authNavGraph(navController = navController,navigateToHome)
        composable<HomeScreen> {
            Home()
        }
    }
}