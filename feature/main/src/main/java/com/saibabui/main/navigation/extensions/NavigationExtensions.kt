package com.saibabui.main.navigation.extensions

import androidx.navigation.NavController
import com.saibabui.main.navigation.graphs.*

// Profile Navigation Extensions
fun NavController.navigateToProfile() {
    navigate(ProfileGraph.ROUTE)
}

// Resume Navigation Extensions
fun NavController.navigateToResumes() {
    navigate(ResumeGraph.ROUTE)
}

fun NavController.navigateToCreateResume() {
    navigate(CreateResumeScreenDestination.ROUTE)
}

fun NavController.navigateToEditResume(resumeId: Int) {
    navigate("edit_resume/$resumeId")
}

// Activity Navigation Extensions
fun NavController.navigateToActivity() {
    navigate(ActivityGraph.ROUTE)
}

// Notification Navigation Extensions
fun NavController.navigateToNotifications() {
    navigate(NotificationGraph.ROUTE)
}

// Collaboration Navigation Extensions
fun NavController.navigateToCollaboration() {
    navigate(CollaborationGraph.ROUTE)
}

// Template Navigation Extensions
fun NavController.navigateToTemplates() {
    navigate(TemplateGraph.ROUTE)
}