package com.saibabui.carryon.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import kotlinx.serialization.Serializable

@Serializable
object Root


@Serializable
object Authentication


@Serializable
object Home


fun NavController.navigateToHome(navOptions: NavOptionsBuilder.() -> Unit = {}) = navigate(Home)