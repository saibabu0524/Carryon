package com.saibabui.main.presentation.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.saibabui.ui.CardView
import com.saibabui.ui.Template


@Composable
fun TemplatesScreen(
    modifier: Modifier = Modifier,
    viewModel: TemplatesViewModel = TemplatesViewModel()
) {
    val templates by viewModel.templates.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val categories = listOf("Professional", "Creative", "Traditional", "Minimalist")
    val currentCategory by viewModel.templates.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                TemplateSelectionButton(
                    buttonText = "All Templates",
                    isSelected = selectedCategory == null,
                    oncClick = { viewModel.setSelectedCategory(null) }
                )
            }
            items(categories) { category ->
                TemplateSelectionButton(
                    buttonText = category,
                    isSelected = selectedCategory == category,
                    oncClick = { viewModel.setSelectedCategory(category) }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(templates) { template ->
                ResumeTemplateCard(
                    template = template,
                    onClick = {}
                )
            }
        }
    }
}


@Composable
fun ResumeTemplateCard(
    template: Template,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CardView(onClick = onClick, modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                        color = Color.DarkGray
                    )
                    .fillMaxWidth()
                    .aspectRatio(1.5f), // Adjusted to match screenshot proportions
                contentAlignment = Alignment.Center
            ) {
                val painter = rememberAsyncImagePainter(model = template.previewImage)
                if (painter.state is coil.compose.AsyncImagePainter.State.Error || painter.state is coil.compose.AsyncImagePainter.State.Empty) {
                    Text(
                        text = "Template Preview",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                } else {
                    Image(
                        painter = painter,
                        contentDescription = template.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = template.name,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(
                        text = template.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Black
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                template.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.LightGray.copy(alpha = 0.5f)
                            )
                    ) {
                        Text(
                            text = tag,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

// Reusing TemplateSelectionButton from your previous code
@Composable
fun TemplateSelectionButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    isSelected: Boolean,
    oncClick: () -> Unit
) {
    Button(
        onClick = oncClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Black else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = CircleShape
    ) {
        Text(text = buttonText)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TemplateScreenPreview() {
    val sampleTemplates = listOf(
        Template(
            id = "1",
            name = "Modern Professional",
            category = "Professional",
            previewImage = "modern_preview.png",
            description = "Clean and minimal design for corporate roles",
            tags = listOf("Modern", "Professional")
        ),
        Template(
            id = "3",
            name = "Creative Portfolio",
            category = "Creative",
            previewImage = "creative_preview.png",
            description = "Stand out with this creative design",
            tags = listOf("Creative", "Design")
        )
    )
    TemplatesScreen()
}