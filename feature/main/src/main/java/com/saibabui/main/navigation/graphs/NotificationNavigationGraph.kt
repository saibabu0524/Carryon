package com.saibabui.main.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saibabui.main.navigation.deeplinks.DeepLinkConstants
import com.saibabui.main.presentation.ui.components.notification.NotificationListScreen
import kotlinx.serialization.Serializable

@Serializable
object NotificationGraph {
    const val ROUTE = "notification_graph"
    const val DEEP_LINK = DeepLinkConstants.NOTIFICATION_DEEP_LINK
}

@Serializable
object NotificationListScreenDestination {
    const val ROUTE = "notification_list"
    const val DEEP_LINK = DeepLinkConstants.NOTIFICATION_DEEP_LINK
}

fun NavController.navigateToNotificationGraph(
    navOptions: androidx.navigation.NavOptions? = null
) {
    navigate(NotificationGraph.ROUTE, navOptions)
}

fun NavGraphBuilder.notificationGraph(
    navController: NavController,
    startDestination: String = NotificationListScreenDestination.ROUTE
) {
    navigation(
        route = NotificationGraph.ROUTE,
        startDestination = startDestination
    ) {
        composable(
            route = NotificationListScreenDestination.ROUTE,
            deepLinks = listOf(
                androidx.navigation.navDeepLink { uriPattern = NotificationListScreenDestination.DEEP_LINK }
            )
        ) {
            NotificationListScreen(
                onNotificationClick = { notification ->
                    // Handle notification click
                }
            )
        }
    }
}