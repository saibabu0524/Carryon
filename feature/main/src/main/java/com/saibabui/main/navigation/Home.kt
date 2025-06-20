package com.saibabui.main.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(
    onNavigateToLogout: suspend () -> Unit = {}
) {
    val navController = rememberNavController()
    HomeScreenWithBottomNavigation(navController, onNavigateToLogout =onNavigateToLogout )
}