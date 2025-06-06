// Updated HomeScreen.kt
package com.saibabui.main.presentation.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saibabui.main.navigation.navigateToResumeScreen
import com.saibabui.mylibrary2.R
import com.saibabui.ui.*
import com.saibabui.ui.shimmer.*

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val recentResumes by viewModel.recentResumes.collectAsState()
    val resumeTips by viewModel.resumeTips.collectAsState()
    val featuredTemplates by viewModel.featuredTemplates.collectAsState()

    val randomTip = remember(resumeTips) { viewModel.getRandomTip() }

    if (isLoading) {
        ShimmerHomeScreen()
    } else {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Welcome back, $userName",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = "Ready to enhance your professional journey?",
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            item {
                HomePremiumSubscriptionCard(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    // Handle premium subscription click
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NewResumeCard(
                        modifier = Modifier.weight(1f),
                        cardTitle = "New Resume",
                        cardDescription = "Create from scratch",
                        cardIcon = R.drawable.google_docs,
                        onClick = {
                            navController.navigateToResumeScreen()
                        }
                    )
                    NewResumeCard(
                        modifier = Modifier.weight(1f),
                        cardTitle = "Template",
                        cardDescription = "Browse designs",
                        cardIcon = R.drawable.google_docs,
                        onClick = {
                            navController.navigateToResumeScreen()
                        }
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recent Activity",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "View All",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (recentResumes.isNotEmpty()) {
                items(recentResumes) { cardData ->
                    HomeScreenRecentCard(
                        resumeTitle = cardData.resumeTitle,
                        template = cardData.template,
                        lastUpdated = cardData.lastUpdated,
                        completionStatus = cardData.completionStatus,
                        onClick = {
                            // Navigate to resume detail screen
                            // navController.navigate("resume_detail/${cardData.id}")
                        }
                    )
                }
            } else {
                item {
                    EmptyRecentActivityCard(
                        onCreateResumeClick = {
                            navController.navigateToResumeScreen()
                        }
                    )
                }
            }

            randomTip?.let { tip ->
                item {
                    ProTipCards(
                        modifier = Modifier.padding(vertical = 16.dp),
                        tip.text
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyRecentActivityCard(
    modifier: Modifier = Modifier,
    onCreateResumeClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = "No recent resumes",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Create your first resume to get started",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryButton(
                onClick = onCreateResumeClick,
                buttonText = "Create Resume",
                modifier = Modifier.fillMaxWidth(0.6f)
            )
        }
    }
}

// Extension function for pull-to-refresh (optional)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWithRefresh(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    // Handle pull-to-refresh
    LaunchedEffect(isLoading) {
        if (isLoading) {
            isRefreshing = false
        }
    }

    // You can implement pull-to-refresh with SwipeRefresh or similar component
    HomeScreen(navController = navController, viewModel = viewModel)
}