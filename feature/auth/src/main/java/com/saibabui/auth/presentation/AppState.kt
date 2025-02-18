package com.saibabui.auth.presentation

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

@Stable
class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    //networkMonitor: NetworkMonitor,
){

}