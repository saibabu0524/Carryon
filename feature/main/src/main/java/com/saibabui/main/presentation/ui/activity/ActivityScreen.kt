package com.saibabui.main.presentation.ui.activity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.saibabui.main.presentation.ui.home.Template
import com.saibabui.main.utils.FilterButtons


@Composable
fun TemplateScreen(
    navController: NavController
) {
    val viewModel: TemplatesViewModel = viewModel()
    TemplatesScreen(navController, viewModel)
}

@Composable
fun TemplatesScreen(navController: NavController, viewModel: TemplatesViewModel = viewModel()) {
    val templates by viewModel.templates.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val categories = listOf("Professional", "Creative", "Traditional", "Minimalist")

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Search Bar
            com.saibabui.main.utils.SearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    viewModel.setSearchQuery(it)
                }
            )

            // Filter Buttons
            FilterButtons(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                    viewModel.setSelectedCategory(it)
                }
            )

            // Templates Grid
            if (templates.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No templates found.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(templates) { template ->
                        TemplateCard(
                            template = template,
                            onClick = {

                            }
                        )
                    }
                }
            }
        }
}

@Composable
fun TemplateCard(template: Template, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = template.name, style = MaterialTheme.typography.titleMedium)
            Text(text = template.category, style = MaterialTheme.typography.bodyMedium)
        }
    }
}