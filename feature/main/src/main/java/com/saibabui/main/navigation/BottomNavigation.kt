package com.saibabui.main.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.saibabui.main.navigation.actions.NavigationActions
import com.saibabui.main.navigation.animations.AnimatedNavHost
import com.saibabui.main.navigation.graphs.*
import com.saibabui.main.presentation.ui.activity.TemplateScreen
import com.saibabui.main.presentation.ui.home.HomeScreen
import com.saibabui.main.presentation.ui.pdfviewer.PdfViewerScreen
import com.saibabui.main.presentation.ui.profile.ProfileScreen
import com.saibabui.main.presentation.ui.resumedetails.ResumeForm
import com.saibabui.main.presentation.ui.resumedetails.ResumeFormScreen
import com.saibabui.main.presentation.ui.resumedetails.navigateToResumeFormNavigation
import com.saibabui.main.presentation.ui.templates.TemplatesScreen
import com.saibabui.main.presentation.ui.transactions.RecentResumesScreen
import com.saibabui.main.utils.PDFViewerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWithBottomNavigation(
    navController: NavHostController,
    onNavigateToLogout: suspend () -> Unit = {}
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (currentDestination?.route) {
                        Home.HomeScreenDestination.route -> Text(text = "Home")
                        Home.ProfileScreenDestination.route -> Text(text = "Profile")
                        Home.ActivityDestination.route -> Text(text = "Activity")
                        Home.TransactionsDestination.route -> Text(text = "Resumes")
                        Home.NotificationDestination.route -> Text(text = "Notifications")
                        Home.CollaborationDestination.route -> Text(text = "Collaboration")
                        Home.TemplateDestination.route -> Text(text = "Templates")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                MainNavDestination.entries.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = screen.resId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.destination.route } == true,
                        onClick = {
                            when (screen) {
                                MainNavDestination.HOME -> {
                                    navController.navigate(Home.HomeScreenDestination.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                MainNavDestination.ACTIVITY -> {
                                    navController.navigate(ActivityGraph.ROUTE) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                MainNavDestination.TRANSACTIONS -> {
                                    navController.navigate(ResumeGraph.ROUTE) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                MainNavDestination.NOTIFICATIONS -> {
                                    navController.navigate(NotificationGraph.ROUTE) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                MainNavDestination.COLLABORATION -> {
                                    navController.navigate(CollaborationGraph.ROUTE) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                MainNavDestination.PROFILE -> {
                                    navController.navigate(Home.ProfileScreenDestination.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        AnimatedNavHost(
            navController = navController,
            startDestination = Home.HomeScreenDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Home screen navigation
            composable(route = Home.HomeScreenDestination.route) {
                HomeScreen(navController)
            }
            
            // Profile screen navigation
            composable(route = Home.ProfileScreenDestination.route) {
                ProfileScreen(onNavigateToLogout = onNavigateToLogout)
            }
            
            // Feature graphs
            activityGraph(navController = navController)
            resumeGraph(navController = navController)
            notificationGraph(navController = navController)
            collaborationGraph(navController = navController)
            profileGraph(navController = navController)
            templateGraph(navController = navController)
            
            // Existing composables from the previous code...
            composable<ResumePreviewScreen> {
                PDFViewerScreen(
                    pdfUrl = "https://www.overleaf.com/latex/templates/rendercv-engineeringresumes-theme/shwqvsxdgkjy.pdf"
                )
            }
            
            composable<TemplatesScreen> {
                TemplatesScreen {
                    navController.navigate(ResumeFormScreen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            }
            
            composable<ResumeFormScreen> {
                ResumeForm {
                    navController.navigateToResumeFormNavigation(
                        navOptions = androidx.navigation.NavOptions.Builder()
                            .setPopUpTo(navController.graph.findStartDestination().id, true)
                            .build()
                    )
                }
            }
        }
    }
}
