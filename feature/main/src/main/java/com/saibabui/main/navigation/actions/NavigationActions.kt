package com.saibabui.main.navigation.actions

import androidx.navigation.NavController
import com.saibabui.main.navigation.extensions.*

/**
 * Navigation actions that can be used throughout the app
 */
class NavigationActions(
    private val navController: NavController
) {
    // Profile actions
    fun navigateToProfile() = navController.navigateToProfile()
    
    // Resume actions
    fun navigateToResumes() = navController.navigateToResumes()
    fun navigateToCreateResume() = navController.navigateToCreateResume()
    fun navigateToEditResume(resumeId: Int) = navController.navigateToEditResume(resumeId)
    
    // Activity actions
    fun navigateToActivity() = navController.navigateToActivity()
    
    // Notification actions
    fun navigateToNotifications() = navController.navigateToNotifications()
    
    
    
    // Template actions
    fun navigateToTemplates() = navController.navigateToTemplates()
    
    // Utility actions
    fun goBack() = navController.popBackStack()
    fun goToRoot() = navController.popBackStack(navController.graph.startDestinationId, false)
}