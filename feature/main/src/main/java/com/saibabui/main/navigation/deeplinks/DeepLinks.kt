package com.saibabui.main.navigation.deeplinks

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.saibabui.main.navigation.Home
import com.saibabui.main.navigation.graphs.*

/**
 * Deep link constants for the app
 */
object DeepLinkConstants {
    const val SCHEME = "carryon"
    const val HOST = "app.carryon.com"
    
    // Profile deep links
    const val PROFILE_PATH = "profile"
    const val PROFILE_DEEP_LINK = "$SCHEME://$HOST/$PROFILE_PATH"
    
    // Resume deep links
    const val RESUME_LIST_PATH = "resumes"
    const val RESUME_DETAIL_PATH = "resumes/{resumeId}"
    const val RESUME_CREATE_PATH = "resumes/create"
    const val RESUME_EDIT_PATH = "resumes/{resumeId}/edit"
    
    const val RESUME_LIST_DEEP_LINK = "$SCHEME://$HOST/$RESUME_LIST_PATH"
    const val RESUME_DETAIL_DEEP_LINK = "$SCHEME://$HOST/$RESUME_DETAIL_PATH"
    const val RESUME_CREATE_DEEP_LINK = "$SCHEME://$HOST/$RESUME_CREATE_PATH"
    const val RESUME_EDIT_DEEP_LINK = "$SCHEME://$HOST/$RESUME_EDIT_PATH"
    
    // Activity deep links
    const val ACTIVITY_PATH = "activity"
    const val ACTIVITY_DEEP_LINK = "$SCHEME://$HOST/$ACTIVITY_PATH"
    
    // Notification deep links
    const val NOTIFICATION_PATH = "notifications"
    const val NOTIFICATION_DEEP_LINK = "$SCHEME://$HOST/$NOTIFICATION_PATH"
    
    // Collaboration deep links
    const val COLLABORATION_PATH = "collaboration"
    const val COLLABORATION_DEEP_LINK = "$SCHEME://$HOST/$COLLABORATION_PATH"
    
    // Template deep links
    const val TEMPLATE_PATH = "templates"
    const val TEMPLATE_DEEP_LINK = "$SCHEME://$HOST/$TEMPLATE_PATH"
}

/**
 * Deep link arguments for navigation
 */
object DeepLinkArguments {
    val resumeIdArgument = navArgument("resumeId") {
        type = NavType.IntType
    }
    
    val templateIdArgument = navArgument("templateId") {
        type = NavType.StringType
        nullable = true
    }
}

/**
 * Utility function to create a deep link URI
 */
fun createDeepLinkUri(path: String, vararg params: Pair<String, String>): Uri {
    val builder = Uri.Builder()
        .scheme(DeepLinkConstants.SCHEME)
        .authority(DeepLinkConstants.HOST)
        .appendPath(path)
    
    params.forEach { (key, value) ->
        builder.appendQueryParameter(key, value)
    }
    
    return builder.build()
}