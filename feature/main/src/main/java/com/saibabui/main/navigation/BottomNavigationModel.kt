package com.saibabui.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.saibabui.home.R

data class BottomNavigationModel(
    val route: String,
    val label: String,
    val icon: Painter
)


enum class MainNavDestination(val icon: ImageVector, val resId: Int, val route: Home) {
    HOME(icon = Icons.Default.Home, resId = R.string.home_nav_lable, route = Home.HomeScreen),
    ACTIVITY(icon = Icons.Default.AccessTimeFilled, resId = R.string.activity_nav_lable, route = Home.Activity),
    TRANSACTIONS(icon = Icons.Default.History, resId = R.string.transactions_nav_lable, route = Home.Transactions),
    PROFILE(icon = Icons.Default.Person, resId = R.string.profile_nav_lable, route = Home.ProfileScreen)
}