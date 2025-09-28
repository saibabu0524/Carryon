package com.saibabui.main.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.saibabui.main.navigation.deeplinks.DeepLinkConstants
import com.saibabui.main.presentation.ui.components.template.TemplateListScreen
import kotlinx.serialization.Serializable

@Serializable
object TemplateGraph {
    const val ROUTE = "template_graph"
    const val DEEP_LINK = DeepLinkConstants.TEMPLATE_DEEP_LINK
}

@Serializable
object TemplateListScreenDestination {
    const val ROUTE = "template_list"
    const val DEEP_LINK = DeepLinkConstants.TEMPLATE_DEEP_LINK
}

fun NavController.navigateToTemplateGraph(
    navOptions: androidx.navigation.NavOptions? = null
) {
    navigate(TemplateGraph.ROUTE, navOptions)
}

fun NavGraphBuilder.templateGraph(
    navController: NavController,
    startDestination: String = TemplateListScreenDestination.ROUTE
) {
    navigation(
        route = TemplateGraph.ROUTE,
        startDestination = startDestination
    ) {
        composable(
            route = TemplateListScreenDestination.ROUTE,
            deepLinks = listOf(
                androidx.navigation.navDeepLink { uriPattern = TemplateListScreenDestination.DEEP_LINK }
            )
        ) {
            TemplateListScreen(
                onTemplateSelected = { template ->
                    // Handle template selection
                }
            )
        }
    }
}