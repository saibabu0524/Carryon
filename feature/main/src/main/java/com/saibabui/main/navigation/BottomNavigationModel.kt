package com.saibabui.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.saibabui.main.R

enum class MainNavDestination(val icon: ImageVector, val resId: Int, val destination: Home) {
    HOME(icon = Icons.Default.Home, resId = R.string.home_nav_lable, destination = Home.HomeScreenDestination),
    ACTIVITY(icon = Icons.Default.AccessTimeFilled, resId = R.string.activity_nav_lable, destination = Home.ActivityDestination),
    TRANSACTIONS(icon = Icons.Default.History, resId = R.string.transactions_nav_lable, destination = Home.TransactionsDestination),
    NOTIFICATIONS(icon = Icons.Default.Notifications, resId = R.string.notifications_nav_label, destination = Home.NotificationDestination),
    COLLABORATION(icon = Icons.Default.People, resId = R.string.collaboration_nav_label, destination = Home.CollaborationDestination),
    PROFILE(icon = Icons.Default.Person, resId = R.string.profile_nav_lable, destination = Home.ProfileScreenDestination)
}
