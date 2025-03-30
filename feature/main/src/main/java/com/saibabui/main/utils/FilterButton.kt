package com.saibabui.main.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterButtons(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { onCategorySelected(null) },
            enabled = selectedCategory != null
        ) {
            Text("All")
        }
        categories.forEach { category ->
            Button(
                onClick = { onCategorySelected(category) },
                enabled = selectedCategory != category
            ) {
                Text(category)
            }
        }
    }
}