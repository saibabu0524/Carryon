package com.saibabui.main.presentation.ui.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.saibabui.main.navigation.navigateToResumeScreen
import com.saibabui.main.presentation.ui.templates.navigateToTemplatesScreens
import com.saibabui.main.utils.FilterButtons
import com.saibabui.ui.TemplatesGrid


@Composable
fun TemplateScreen(
    navController: NavController
) {
    val viewModel: TemplatesViewModel = viewModel()
    TemplatesScreen(navController, viewModel)
}

@Composable
fun TemplatesScreen(
    navController: NavController,
    viewModel: TemplatesViewModel = viewModel()
) {
    val templates by viewModel.templates.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val categories = listOf("Professional", "Creative", "Traditional", "Minimalist")

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
//        com.saibabui.main.utils.SearchBar(
//            query = searchQuery,
//            onQueryChange = {
//                searchQuery = it
//                viewModel.setSearchQuery(it)
//            }
//        )

        FilterButtons(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = {
                selectedCategory = it
                viewModel.setSelectedCategory(it)
            }
        )
        
        TemplatesGrid(
            templates = templates,
            modifier = Modifier,
            onTemplateClick = { template ->
                navController.navigateToTemplatesScreens()
            }
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TemplateScreenPreview() {
    TemplateScreen(navController = NavController(LocalContext.current))
}