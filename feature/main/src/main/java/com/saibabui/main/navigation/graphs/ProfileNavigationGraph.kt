package com.saibabui.main.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saibabui.main.navigation.Home
import com.saibabui.main.navigation.deeplinks.DeepLinkConstants
import com.saibabui.main.presentation.ui.components.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
object ProfileGraph {
    const val ROUTE = "profile_graph"
    const val DEEP_LINK = DeepLinkConstants.PROFILE_DEEP_LINK
}

@Serializable
object ProfileScreenDestination {
    const val ROUTE = "profile"
    const val DEEP_LINK = DeepLinkConstants.PROFILE_DEEP_LINK
}

fun NavController.navigateToProfileGraph(
    navOptions: androidx.navigation.NavOptions? = null
) {
    navigate(ProfileGraph.ROUTE, navOptions)
}

fun NavGraphBuilder.profileGraph(
    navController: NavController,
    startDestination: String = ProfileScreenDestination.ROUTE
) {
    navigation(
        route = ProfileGraph.ROUTE,
        startDestination = startDestination
    ) {
        composable(
            route = ProfileScreenDestination.ROUTE,
            deepLinks = listOf(
                androidx.navigation.navDeepLink { uriPattern = ProfileScreenDestination.DEEP_LINK }
            )
        ) {
            ProfileScreen()
        }
    }
}