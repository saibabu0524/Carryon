package com.saibabui.main.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    Scaffold(
        topBar = {
            TopAppBar(title = {
                when (currentDestination?.route) {
                    Home.HomeScreenDestination.route -> Text(text = "Home")
                    Home.ProfileScreenDestination.route -> Text(text = "Profile")
                    Home.ActivityDestination.route -> Text(text = "My Resumes")
                    Home.TransactionsDestination.route -> Text(text = "Templates")
                }
            })
        },
        bottomBar = {
            NavigationBar {
                MainNavDestination.entries.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(text = stringResource(id = screen.resId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.destination.route } == true,
                        onClick = {
                            navController.navigate(screen.destination.route) {
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
            navController = navController,
            startDestination = Home.HomeScreenDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Home.HomeScreenDestination.route) {
                HomeScreen(navController)
            }
            composable(route = Home.ProfileScreenDestination.route) {
//                ProfileScreen(navController)
                ProfileScreen(onNavigateToLogout = onNavigateToLogout)
            }
            composable(route = Home.ActivityDestination.route) {
                TemplateScreen(navController)
            }
            composable(route = Home.TransactionsDestination.route) {
                RecentResumesScreen(navController)
            }
            composable<ResumePreviewScreen> {
                PDFViewerScreen(
                    pdfUrl = "https://www.overleaf.com/latex/templates/rendercv-engineeringresumes-theme/shwqvsxdgkjy.pdf"
                )
//                PdfViewerScreen(
//
//                )
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
                        navOptions = NavOptions.Builder()
                            .setPopUpTo(navController.graph.findStartDestination().id, true)
                            .build()
                    )
                }
            }
        }
    }
}
