package com.saibabui.main.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saibabui.main.navigation.deeplinks.DeepLinkConstants
import com.saibabui.main.presentation.ui.components.collaboration.CollaboratorListScreen
import kotlinx.serialization.Serializable

@Serializable
object CollaborationGraph {
    const val ROUTE = "collaboration_graph"
    const val DEEP_LINK = DeepLinkConstants.COLLABORATION_DEEP_LINK
}

@Serializable
object CollaboratorListScreenDestination {
    const val ROUTE = "collaborator_list"
    const val DEEP_LINK = DeepLinkConstants.COLLABORATION_DEEP_LINK
}

fun NavController.navigateToCollaborationGraph(
    navOptions: androidx.navigation.NavOptions? = null
) {
    navigate(CollaborationGraph.ROUTE, navOptions)
}

fun NavGraphBuilder.collaborationGraph(
    navController: NavController,
    startDestination: String = CollaboratorListScreenDestination.ROUTE
) {
    navigation(
        route = CollaborationGraph.ROUTE,
        startDestination = startDestination
    ) {
        composable(
            route = CollaboratorListScreenDestination.ROUTE,
            deepLinks = listOf(
                androidx.navigation.navDeepLink { uriPattern = CollaboratorListScreenDestination.DEEP_LINK }
            )
        ) {
            CollaboratorListScreen(
                onResumeSelected = { resumeId ->
                    // Handle resume selection
                }
            )
        }
    }
}