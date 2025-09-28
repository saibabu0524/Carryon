package com.saibabui.main.navigation.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

/**
 * Navigation animations for smooth transitions between screens
 */
object NavigationAnimations {
    // Fade in/out animation
    @Composable
    fun fadeInOutEnterTransition(): EnterTransition {
        return fadeIn(
            animationSpec = tween(300)
        )
    }

    @Composable
    fun fadeInOutExitTransition(): ExitTransition {
        return fadeOut(
            animationSpec = tween(300)
        )
    }

    // Slide in/out animation
    @Composable
    fun slideInRightExitTransition(): ExitTransition {
        return slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.End,
            animationSpec = tween(300)
        ) + fadeOut(tween(300))
    }

    @Composable
    fun slideInLeftEnterTransition(): EnterTransition {
        return slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            animationSpec = tween(300)
        ) + fadeIn(tween(300))
    }

    // Scale animation
    @Composable
    fun scaleInEnterTransition(): EnterTransition {
        return scaleIn(
            initialScale = 0.9f,
            animationSpec = tween(300)
        ) + fadeIn(tween(300))
    }

    @Composable
    fun scaleOutExitTransition(): ExitTransition {
        return scaleOut(
            targetScale = 0.9f,
            animationSpec = tween(300)
        ) + fadeOut(tween(300))
    }

    // Combined animations
    @Composable
    fun defaultEnterTransition(): EnterTransition {
        return fadeInOutEnterTransition()
    }

    @Composable
    fun defaultExitTransition(): ExitTransition {
        return fadeInOutExitTransition()
    }

    // Animation specs for navigation transitions
    const val DEFAULT_ANIMATION_DURATION = 300
}

/**
 * Custom NavHost with animations
 */
@Composable
fun AnimatedNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier,
    contentAlignment: androidx.compose.ui.Alignment = androidx.compose.ui.Alignment.Center,
    route: String? = null,
    content: androidx.navigation.compose.NavGraphBuilder.() -> Unit
) {
    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        contentAlignment = contentAlignment,
        route = route,
        enterTransition = {
            NavigationAnimations.defaultEnterTransition()
        },
        exitTransition = {
            NavigationAnimations.defaultExitTransition()
        },
        popEnterTransition = {
            NavigationAnimations.slideInLeftEnterTransition()
        },
        popExitTransition = {
            NavigationAnimations.slideInRightExitTransition()
        },
        content = content
    )
}

/**
 * Custom animated composable for screens with enter/exit animations
 */
@Composable
fun AnimatedComposable(
    route: String,
    arguments: List<androidx.navigation.NamedNavArgument> = emptyList(),
    deepLinks: List<androidx.navigation.NavDeepLink> = emptyList(),
    content: @Composable (androidx.navigation.NavBackStackEntry) -> Unit
) {
    androidx.navigation.compose.composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            NavigationAnimations.defaultEnterTransition()
        },
        exitTransition = {
            NavigationAnimations.defaultExitTransition()
        },
        popEnterTransition = {
            NavigationAnimations.slideInLeftEnterTransition()
        },
        popExitTransition = {
            NavigationAnimations.slideInRightExitTransition()
        },
        content = content
    )
}