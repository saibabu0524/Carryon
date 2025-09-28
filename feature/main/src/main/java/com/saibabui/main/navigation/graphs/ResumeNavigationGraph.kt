package com.saibabui.main.navigation.graphs

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.saibabui.main.navigation.deeplinks.DeepLinkConstants
import com.saibabui.main.presentation.ui.components.resume.CreateEditResumeScreen
import com.saibabui.main.presentation.ui.components.resume.ResumeListScreen
import kotlinx.serialization.Serializable

@Serializable
object ResumeGraph {
    const val ROUTE = "resume_graph"
}

@Serializable
object ResumeListScreenDestination {
    const val ROUTE = "resume_list"
    const val DEEP_LINK = DeepLinkConstants.RESUME_LIST_DEEP_LINK
}

@Serializable
object CreateResumeScreenDestination {
    const val ROUTE = "create_resume"
    const val DEEP_LINK = DeepLinkConstants.RESUME_CREATE_DEEP_LINK
}

@Serializable
object EditResumeScreenDestination {
    const val ROUTE = "edit_resume/{resumeId}"
    const val DEEP_LINK = DeepLinkConstants.RESUME_EDIT_DEEP_LINK
}

fun NavController.navigateToResumeGraph(
    navOptions: androidx.navigation.NavOptions? = null
) {
    navigate(ResumeGraph.ROUTE, navOptions)
}

fun NavController.navigateToCreateResume() {
    navigate(CreateResumeScreenDestination.ROUTE)
}

fun NavController.navigateToEditResume(resumeId: Int) {
    navigate("edit_resume/$resumeId")
}

fun NavGraphBuilder.resumeGraph(
    navController: NavController,
    startDestination: String = ResumeListScreenDestination.ROUTE
) {
    navigation(
        route = ResumeGraph.ROUTE,
        startDestination = startDestination
    ) {
        composable(
            route = ResumeListScreenDestination.ROUTE,
            deepLinks = listOf(
                androidx.navigation.navDeepLink { uriPattern = ResumeListScreenDestination.DEEP_LINK }
            )
        ) {
            ResumeListScreen(
                viewModel = androidx.hilt.navigation.compose.hiltViewModel(),
                onNavigateToCreateResume = { navController.navigateToCreateResume() },
                onNavigateToEditResume = { resume -> 
                    navController.navigateToEditResume(resume.id) 
                }
            )
        }
        
        composable(
            route = CreateResumeScreenDestination.ROUTE,
            deepLinks = listOf(
                androidx.navigation.navDeepLink { uriPattern = CreateResumeScreenDestination.DEEP_LINK }
            )
        ) {
            CreateEditResumeScreen(
                onSave = { title, templateId -> 
                    // Save and navigate back
                    navController.popBackStack()
                },
                onCancel = { 
                    navController.popBackStack() 
                }
            )
        }
        
        composable(
            route = EditResumeScreenDestination.ROUTE,
            arguments = listOf(
                navArgument("resumeId") { type = androidx.navigation.NavType.IntType }
            ),
            deepLinks = listOf(
                androidx.navigation.navDeepLink { uriPattern = EditResumeScreenDestination.DEEP_LINK }
            )
        ) { backStackEntry ->
            val resumeId = backStackEntry.arguments?.getInt("resumeId") ?: 0
            CreateEditResumeScreen(
                onSave = { title, templateId -> 
                    // Save and navigate back
                    navController.popBackStack()
                },
                onCancel = { 
                    navController.popBackStack() 
                }
            )
        }
    }
}