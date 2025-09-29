package com.saibabui.main.presentation.ui.templates

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.saibabui.main.presentation.viewmodels.template.TemplateViewModel
import com.saibabui.ui.ResumeTemplateUi

@Composable
fun TemplatesScreen(
    modifier: Modifier = Modifier,
    templateViewModel: TemplateViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    oncClick: () -> Unit
) {
    val uiState by templateViewModel.uiState.collectAsState()
    val templates = uiState.templates

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Category selection can be added here if needed with CategoryViewModel
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                TemplateSelectionButton(
                    buttonText = "All Templates",
                    isSelected = true,
                    oncClick = { /* TODO: Implement category filtering if needed */ }
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
                    onClick = {oncClick()}
                )
            }
        }
    }
}

@Composable
fun ResumeTemplateCard(
    template: ResumeTemplateUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                contentAlignment = Alignment.Center
            ) {
                val painter = rememberAsyncImagePainter(model = template.templateImage) // Use templateImage from ResumeTemplateUi
                if (painter.state is coil.compose.AsyncImagePainter.State.Error || painter.state is coil.compose.AsyncImagePainter.State.Empty) {
                    Text(
                        text = "Template Preview",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    )
                } else {
                    Image(
                        painter = painter,
                        contentDescription = template.templateName, // Use templateName from ResumeTemplateUi
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
                        text = template.templateName, // Use templateName from ResumeTemplateUi
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = template.description, // Use description from ResumeTemplateUi
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

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
        shape = CircleShape
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TemplateScreenPreview() {
    val sampleTemplates = listOf(
        ResumeTemplateUi(
            id = 1,
            templateName = "Modern Professional",
            description = "Clean and minimal design for corporate roles",
            templateImage = "modern_preview.png",
            isPremium = false,
            categoryId = 1,
            userId = 1
        ),
        ResumeTemplateUi(
            id = 2,
            templateName = "Creative Portfolio",
            description = "Stand out with this creative design",
            templateImage = "creative_preview.png",
            isPremium = true,
            categoryId = 2,
            userId = 1
        )
    )
    TemplatesScreen{}
}