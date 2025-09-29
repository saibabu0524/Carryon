package com.saibabui.carryon.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.dataStore
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saibabui.auth.presentation.ui.dashboard.DashboardNavigation
import com.saibabui.datastore.UserPreferences
import com.saibabui.main.navigation.Home
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import com.saibabui.carryon.navigation.Home as HomeScreen

@Serializable
object Loading


@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startDestination: Boolean,
    dataStore: UserPreferences,
    navigateToHome: () -> Unit
) {
    NavHost(
        navController = navController,
//        startDestination = HomeScreen,
        startDestination = if (startDestination) HomeScreen else Authentication
    ) {
        composable<Loading> {
            LaunchedEffect(Unit) {
                val token = dataStore.accessToken.first()
                if (token.isNotEmpty()) {
                    navController.navigateToHome {
                        popUpTo(Loading) { inclusive = true }
                    }
                } else {
                    navController.navigate(Authentication) {
                        popUpTo(Loading) { inclusive = true }
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        authNavGraph(navController = navController, navigateToHome)
        composable<HomeScreen> {
            Home(
                onNavigateToLogout = {
                    dataStore.updateAccessToken("")
                    navController.navigate(Authentication) {
                        popUpTo(HomeScreen) { inclusive = true }
                    }
                }
            )
        }
    }
}