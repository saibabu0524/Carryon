package com.saibabui.main.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saibabui.main.navigation.deeplinks.DeepLinkConstants
import com.saibabui.main.presentation.ui.components.activity.ActivityListScreen
import com.saibabui.main.presentation.ui.components.activity.AnalyticsDashboard
import kotlinx.serialization.Serializable

@Serializable
object ActivityGraph {
    const val ROUTE = "activity_graph"
    const val DEEP_LINK = DeepLinkConstants.ACTIVITY_DEEP_LINK
}

@Serializable
object ActivityListScreenDestination {
    const val ROUTE = "activity_list"
    const val DEEP_LINK = DeepLinkConstants.ACTIVITY_DEEP_LINK
}

@Serializable
object AnalyticsDashboardDestination {
    const val ROUTE = "analytics_dashboard"
}

fun NavController.navigateToActivityGraph(
    navOptions: androidx.navigation.NavOptions? = null
) {
    navigate(ActivityGraph.ROUTE, navOptions)
}

fun NavGraphBuilder.activityGraph(
    navController: NavController,
    startDestination: String = ActivityListScreenDestination.ROUTE
) {
    navigation(
        route = ActivityGraph.ROUTE,
        startDestination = startDestination
    ) {
        composable(
            route = ActivityListScreenDestination.ROUTE,
            deepLinks = listOf(
                androidx.navigation.navDeepLink { uriPattern = ActivityListScreenDestination.DEEP_LINK }
            )
        ) {
            ActivityListScreen(
                onActivityClick = { activity ->
                    // Handle activity click
                }
            )
        }
        
        composable(
            route = AnalyticsDashboardDestination.ROUTE
        ) {
            AnalyticsDashboard()
        }
    }
}