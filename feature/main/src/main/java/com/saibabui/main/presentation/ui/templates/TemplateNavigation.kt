package com.saibabui.main.presentation.ui.templates

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
object TemplatesScreen


fun NavController.navigateToTemplatesScreens(navOptions: NavOptions? = null) =
    navigate(route = TemplatesScreen, navOptions)