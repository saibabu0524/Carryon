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
            NavigationBar(
                modifier = Modifier
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                MainNavDestination.entries.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = screen.resId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route.route } == true,
                        onClick = {
                            navController.navigate(screen.route.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
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
            navController,
            startDestination = Home.HomeScreen.route,
            Modifier.padding(paddingValues)
        ) {
            composable(Home.HomeScreen.route) {
                HomeScreen(navController, paddingValues)
            }
            composable(Home.ProfileScreen.route) {
                ProfileScreen(navController, paddingValues)
            }
            composable(Home.Activity.route) {
                ActivityScreen(navController, paddingValues)
            }
            composable(Home.Transactions.route) {
                TransactionScreen(navController, paddingValues)
            }
        }
    }
}

//
//@Composable
//fun BottomBar(navController: NavHostController) {
//    val items = listOf(
//        BottomNavigationModel(
//            Home.HomeScreen.route,
//            "Home",
//            painterResource(id = R.drawable.profile_nav_icon)
//        ),
//        BottomNavigationModel(
//            Home.Activity.route,
//            "Activity",
//            painterResource(id = R.drawable.profile_nav_icon)
//        ),
//        BottomNavigationModel(
//            Home.Transactions.route,
//            "Transactions",
//            painterResource(id = R.drawable.profile_nav_icon)
//        ),
//        BottomNavigationModel(
//            Home.ProfileScreen.route,
//            "Profile",
//            painterResource(id = R.drawable.profile_nav_icon)
//        )
//    )
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//
//    val bottomBarDestination = items.any { it.route == currentDestination?.route }
//    if (bottomBarDestination) {
//        BottomNavigation(
//            backgroundColor = MaterialTheme.colorScheme.background,
//            contentColor = MaterialTheme.colorScheme.secondary,
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(MaterialTheme.colorScheme.background)
//        ) {
//            items.forEach { screen ->
//                AddItem(
//                    screen = screen,
//                    currentDestination = currentDestination,
//                    navController = navController
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//fun RowScope.AddItem(
//    screen: BottomNavigationModel,
//    currentDestination: NavDestination?,
//    navController: NavHostController
//) {
//    BottomNavigationItem(
//        label = {
//            Text(
//                text = screen.label,
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onBackground,
//            )
//        },
//        icon = {
//            androidx.compose.material.Icon(
//                painter = screen.icon,
//                contentDescription = "Navigation Icon",
//                tint = if (currentDestination?.hierarchy?.any {
//                        it.route == screen.route
//                    } == true) {
//                    MaterialTheme.colorScheme.primary
//                } else {
//                    LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
//                }
//            )
//        },
//        selected = currentDestination?.hierarchy?.any {
//            it.route == screen.route
//        } == true,
//        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
//        onClick = {
//            navController.navigate(screen.route) {
//                popUpTo(navController.graph.findStartDestination().id)
//                launchSingleTop = true
//            }
//        },
//        modifier = Modifier.padding(vertical = 10.dp)
//    )
//}