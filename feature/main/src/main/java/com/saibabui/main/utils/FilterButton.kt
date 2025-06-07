package com.saibabui.main.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterButtons(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            TemplateSelectionButton(
                buttonText = "All",
                isSelected = selectedCategory == null,
                oncClick = { onCategorySelected(null) }
            )
        }
        items(categories){ category ->
            TemplateSelectionButton(
                buttonText = category,
                isSelected = selectedCategory == category,
                oncClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun TemplateSelectionButton(
    buttonText: String,
    isSelected: Boolean,
    oncClick: () -> Unit
) {
    Button(
        onClick = oncClick,
        modifier = Modifier
            .height(36.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        ),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterButtonsPreview() {
    FilterButtons(
        categories = listOf("Modern", "Classic", "Creative"),
        selectedCategory = "Modern",
        onCategorySelected = {}
    )
}