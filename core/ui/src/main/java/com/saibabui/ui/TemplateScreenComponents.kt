package com.saibabui.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.saibabui.mylibrary2.R

// Template data class
data class Template(
    val id: String,
    val name: String,
    val category: String,
    val previewImage: String,
    val completionPercentage: Int = 0,
    val description: String = "",
    val tags: List<String> = emptyList()
)

@Composable
fun TemplateSelectionButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    isSelected: Boolean,
    oncClick: () -> Unit
) {
    Button(
        onClick = oncClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        ),
        shape = CircleShape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
private fun TemplateSelectionButtonPreview() {
    TemplateSelectionButton(buttonText = "Modern", isSelected = true) {}
}

@Composable
fun TemplateTypeCard(
    modifier: Modifier = Modifier,
    template: Template,
    onClick: () -> Unit
) {
    CardView(onClick = onClick, modifier = modifier) {
        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                )
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.template_preview),
                contentDescription = template.name,
                modifier = Modifier,
                contentScale = ContentScale.FillBounds
            )
        }

        Text(
            text = template.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
                .background(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                )
        ) {
            Text(
                text = template.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun TemplatesGrid(
    templates: List<Template>,
    modifier: Modifier = Modifier,
    onTemplateClick: (Template) -> Unit
) {
    if (templates.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No templates found.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(templates) { template ->
                TemplateTypeCard(
                    template = template,
                    onClick = { onTemplateClick(template) },
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TemplatesGridPreview() {
    val sampleTemplates = listOf(
        Template(
            id = "1",
            name = "Modern Resume",
            category = "Modern",
            previewImage = "template_preview"
        ),
        Template(
            id = "2",
            name = "Classic Resume",
            category = "Classic",
            previewImage = "template_preview"
        )
    )
    TemplatesGrid(templates = sampleTemplates, onTemplateClick = {})
}