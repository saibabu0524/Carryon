package com.saibabui.main.presentation.ui.components.template

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saibabui.main.presentation.viewmodels.template.TemplateViewModel
import com.saibabui.main.presentation.viewmodels.template.TemplateUiState
import com.saibabui.network.home.model.TemplateResponse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateListScreen(
    viewModel: TemplateViewModel = hiltViewModel(),
    onTemplateSelected: (TemplateResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Handle state changes
    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            // Show error snackbar
            println("Error: $errorMessage")
        }
    }
    
    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let { successMessage ->
            // Show success snackbar
            println("Success: $successMessage")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Templates",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.refreshTemplates() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle custom template creation */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add custom template"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && uiState.templates.isEmpty() -> {
                    LoadingIndicator()
                }
                uiState.error != null && uiState.templates.isEmpty() -> {
                    ErrorScreen(
                        errorMessage = uiState.error!!,
                        onRetry = { viewModel.loadTemplates() }
                    )
                }
                else -> {
                    TemplateListContent(
                        uiState = uiState,
                        onRefresh = { viewModel.refreshTemplates() },
                        onFilterChanged = { category ->
                            viewModel.filterByCategory(category)
                        },
                        onTemplateClick = onTemplateSelected,
                        onUseTemplate = { template ->
                            // Handle use template action
                            onTemplateSelected(template)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TemplateListContent(
    uiState: TemplateUiState,
    onRefresh: () -> Unit,
    onFilterChanged: (String?) -> Unit,
    onTemplateClick: (TemplateResponse) -> Unit,
    onUseTemplate: (TemplateResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Filter chips
        if (uiState.categories.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = uiState.selectedCategory == null,
                        onClick = { onFilterChanged(null) },
                        label = { Text("All") }
                    )
                }
                
                items(uiState.categories) { category ->
                    FilterChip(
                        selected = uiState.selectedCategory == category,
                        onClick = { 
                            onFilterChanged(
                                if (uiState.selectedCategory == category) null else category
                            )
                        },
                        label = { Text(category) }
                    )
                }
            }
        }
        
        // Template grid
        val filteredTemplates = if (uiState.selectedCategory != null) {
            uiState.templates.filter { 
                (it as? Map<String, Any>)?.get("category") == uiState.selectedCategory 
            }
        } else {
            uiState.templates
        }
        
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item(span = { GridItemSpan(maxLineSpan) }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Available Templates",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    if (filteredTemplates.isNotEmpty()) {
                        Text(
                            text = "${filteredTemplates.size} templates",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Template items
            if (filteredTemplates.isEmpty()) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    if (!uiState.isLoading) {
                        EmptyState(
                            onRetry = onRefresh
                        )
                    }
                }
            } else {
                items(filteredTemplates) { templateMap ->
                    // Convert Map to TemplateResponse for display purposes
                    val template = mapToTemplateResponse(templateMap as Map<String, Any>)
                    TemplateListItem(
                        template = template,
                        onClick = onTemplateClick,
                        onUseClick = onUseTemplate,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                
                // Loading indicator for pagination
                if (uiState.isLoading && filteredTemplates.isNotEmpty()) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

// Helper function to convert Map to TemplateResponse
private fun mapToTemplateResponse(map: Map<String, Any>): TemplateResponse {
    return TemplateResponse(
        id = map["id"] as? Int ?: 0,
        name = map["name"] as? String ?: "Unknown Template",
        description = map["description"] as? String,
        category = map["category"] as? String,
        thumbnailUrl = map["thumbnail_url"] as? String,
        isFree = map["is_free"] as? Boolean ?: true,
        isPopular = map["is_popular"] as? Boolean ?: false,
        usageCount = (map["usage_count"] as? Number)?.toInt() ?: 0,
        createdAt = map["created_at"] as? String ?: "",
        updatedAt = map["updated_at"] as? String ?: ""
    )
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator()
            Text("Loading templates...")
        }
    }
}

@Composable
private fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyState(
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Description,
            contentDescription = "No templates",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "No templates found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = "Try selecting a different category or refresh to see more templates",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onRetry) {
            Text("Refresh")
        }
    }
}