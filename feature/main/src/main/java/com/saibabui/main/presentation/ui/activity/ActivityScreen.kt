package com.saibabui.main.presentation.ui.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saibabui.main.navigation.navigateToResumeScreen
import com.saibabui.main.presentation.ui.templates.navigateToTemplatesScreens
import com.saibabui.main.presentation.viewmodels.template.TemplateViewModel
import com.saibabui.ui.ResumeTemplateUi
import com.saibabui.main.utils.FilterButtons
import com.saibabui.ui.TemplatesGrid
import com.saibabui.ui.TemplatesGridWithResumeTemplate
import com.saibabui.ui.shimmer.ShimmerTemplateCard

@Composable
fun TemplateScreen(
    navController: NavController,
    templateViewModel: TemplateViewModel = hiltViewModel()
) {
    val uiState by templateViewModel.uiState.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        templateViewModel.loadTemplates()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Add category filtering here if needed with CategoryViewModel
        // For now, use a simple approach

        // Content
        when {
            uiState.isLoading -> {
                ShimmerTemplatesGrid()
            }

            uiState.error != null -> {
                uiState.error?.let {
                    ErrorContent(
                        error = it,
                        onRetry = templateViewModel::loadTemplates
                    )
                }
            }

            uiState.templates.isEmpty() -> {
                EmptyContent(
                    selectedCategory = selectedCategory,
                    onClearFilter = {
                        selectedCategory = null
                    }
                )
            }

            else -> {
                TemplatesGridWithResumeTemplate(
                    templates = uiState.templates,
                    onTemplateClick = { template ->
                        // Handle template selection
                        navController.navigateToTemplatesScreens()
                    }
                )
            }
        }
    }
}

@Composable
private fun ShimmerTemplatesGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(6) { // Show 6 shimmer items
            ShimmerTemplateCard()
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Failed to load templates",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text("Try Again")
        }
    }
}

@Composable
private fun EmptyContent(
    selectedCategory: String?,
    onClearFilter: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (selectedCategory != null) {
                "No templates found in \"$selectedCategory\" category"
            } else {
                "No templates available"
            },
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (selectedCategory != null) {
                "Try selecting a different category or clear the filter"
            } else {
                "Templates will appear here once they're loaded"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (selectedCategory != null) {
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                onClick = onClearFilter,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Clear Filter")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TemplateScreenPreview() {
    TemplateScreen(navController = NavController(LocalContext.current))
}