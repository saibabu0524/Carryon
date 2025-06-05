package com.saibabui.main.presentation.ui.resumedetails

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
object ResumeFormScreen


fun NavController.navigateToResumeFormNavigation(navOptions: NavOptions? = null) =
    navigate(route = ResumeFormScreen, navOptions)