package com.saibabui.main.presentation.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.saibabui.main.navigation.Home

@Composable
fun ProfileScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    ProfileScreen(paddingValues)
}


@Composable
fun ProfileScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Profile Screen")
    }
}