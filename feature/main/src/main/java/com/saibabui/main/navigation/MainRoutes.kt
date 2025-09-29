package com.saibabui.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.saibabui.main.navigation.graphs.ProfileGraph
import com.saibabui.main.navigation.graphs.ResumeGraph
import com.saibabui.main.navigation.graphs.ActivityGraph
import com.saibabui.main.navigation.graphs.NotificationGraph
import com.saibabui.main.navigation.graphs.TemplateGraph
import kotlinx.serialization.Serializable


@Serializable
sealed class Home(val route: String) {
    @Serializable
    object HomeScreenDestination : Home("home_screen")

    @Serializable
    object ActivityDestination : Home(ActivityGraph.ROUTE)

    @Serializable
    object TransactionsDestination : Home(ResumeGraph.ROUTE)

    @Serializable
    object ProfileScreenDestination : Home(ProfileGraph.ROUTE)
    
    @Serializable
    object NotificationDestination : Home(NotificationGraph.ROUTE)
    
    @Serializable
    object TemplateDestination : Home(TemplateGraph.ROUTE)
}


@Serializable
object ResumePreviewScreen


fun NavController.navigateToResumeScreen(navOptions: NavOptions? = null) =
    navigate(route = ResumePreviewScreen, navOptions)