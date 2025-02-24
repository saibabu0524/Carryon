package com.saibabui.main.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.saibabui.main.presentation.ui.activity.ActivityScreen
import com.saibabui.main.presentation.ui.home.HomeScreen
import com.saibabui.main.presentation.ui.profile.ProfileScreen
import com.saibabui.main.presentation.ui.transactions.TransactionScreen

@Composable
fun HomeScreenWithBottomNavigation(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                MainNavDestination.entries.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = screen.resId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.destination.route } == true,
                        onClick = {
                            navController.navigate(screen.destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Home.HomeScreenDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Home.HomeScreenDestination.route) {
                HomeScreen(navController, paddingValues)
            }
            composable(route = Home.ProfileScreenDestination.route) {
                ProfileScreen(navController, paddingValues)
            }
            composable(route = Home.ActivityDestination.route) {
                ActivityScreen(navController, paddingValues)
            }
            composable(route = Home.TransactionsDestination.route) {
                TransactionScreen(navController, paddingValues)
            }
        }
    }
}
