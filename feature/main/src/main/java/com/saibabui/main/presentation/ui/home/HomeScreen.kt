package com.saibabui.main.presentation.ui.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.saibabui.main.navigation.navigateToResumeScreen
import com.saibabui.mylibrary2.R
import com.saibabui.ui.PrimaryButton
import com.saibabui.ui.SecondaryButton


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val userName by viewModel.userName.collectAsState()
    val recentResumes by viewModel.recentResumes.collectAsState()
    val resumeTips by viewModel.resumeTips.collectAsState()
    val featuredTemplates by viewModel.featuredTemplates.collectAsState()

    val randomTip = remember { resumeTips.randomOrNull() }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // Increased spacing for clarity
    ) {
        item {
            Text(
                text = "Welcome, $userName",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
            )
        }
        item {
            Text(
                text = "Featured Templates",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between cards
            ) {
                items(featuredTemplates) { template ->
                    ResumeTemplateCard(
                        template = Template(
                            id = template.id,
                            name = template.name,
                            category = template.category,
                            previewImage = "https://careers.dasa.ncsu.edu/wp-content/uploads/sites/37/2023/07/Communications-major-resume-example.jpg"
                        ),
                        onClick = {
                            navController.navigateToResumeScreen()
                        }
                    )
                }
            }
        }
        item {
            Text(
                text = "Success Stories",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between cards
            ) {
                items(featuredTemplates) { template ->
                    ResumeTemplateCard(
                        template = Template(
                            id = template.id,
                            name = template.name,
                            category = template.category,
                            previewImage = "https://careers.dasa.ncsu.edu/wp-content/uploads/sites/37/2023/07/Communications-major-resume-example.jpg"
                        ),
                        onClick = {
                            // TODO("Navigation to template screen")
                        }
                    )
                }
            }
        }
        item {
            Text(
                text = "Most Used Tools",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
            )
        }
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Space between cards
            ) {
                items(featuredTemplates) { template ->
                    ResumeTemplateCard(
                        template = Template(
                            id = template.id,
                            name = template.name,
                            category = template.category,
                            previewImage = "https://careers.dasa.ncsu.edu/wp-content/uploads/sites/37/2023/07/Communications-major-resume-example.jpg"
                        ),
                        onClick = {
                            // TODO("Navigation to template screen")
                        }
                    )
                }
            }
        }

        // Recent Resumes section
        if (recentResumes.isNotEmpty()) {
            item {
                Text(
                    text = "Recent Resumes",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(recentResumes.take(3)) { resume ->
                ResumeCard(
                    resume = resume,
                    onEditClick = {
                        // TODO("Navigation to template screen")
                    },
                    onViewClick = {

                    }
                )
            }
            item {
                PrimaryButton(
                    onClick = {  },
                    buttonText = "View All Resumes",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            item {
                Text(
                    text = "No resumes yet. Create your first resume!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                PrimaryButton(
                    onClick = {  },
                    buttonText = "Create Resume",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Featured Templates section


        // Resume Tip section
        randomTip?.let { tip ->
            item {
                ResumeTipCard(tip)
            }
        }
    }
}


@Composable
fun ResumeCard(resume: Resume, onEditClick: () -> Unit, onViewClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = resume.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Last modified: ${resume.lastModified}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                PrimaryButton(onClick = onEditClick, buttonText = "Edit")
                Spacer(modifier = Modifier.width(8.dp))
                SecondaryButton(onClick = onViewClick, buttonText = "View")
            }
        }
    }
}

@Composable
fun ResumeTipCard(tip: Tip) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Text(
            text = tip.text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ResumeTemplateCard(
    modifier: Modifier = Modifier,
    template: Template,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val roundedCornerShape = RoundedCornerShape(12.dp)
    val elevation = animateDpAsState(
        targetValue = if (interactionSource.collectIsPressedAsState().value) 2.dp else 8.dp,
        label = "cardElevation"
    )

    Surface(
        modifier = modifier
            .width(240.dp)  // Fixed width
            .height(360.dp)
            .clip(roundedCornerShape)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                onClick = onClick
            ),
        shape = roundedCornerShape,
        shadowElevation = elevation.value,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            // Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(roundedCornerShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(template.previewImage)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Template preview",
                    placeholder = painterResource(R.drawable.google_docs),
                    error = painterResource(R.drawable.google_docs),
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Text Content
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = template.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Badge(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Text(
                        text = "FREE",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun CardPreview(modifier: Modifier = Modifier) {
    ResumeTemplateCard(
        template = Template(
            id = "1",
            name = "Sample Template",
            category = "Category",
            previewImage = "https://careers.dasa.ncsu.edu/wp-content/uploads/sites/37/2023/07/Communications-major-resume-example.jpg"
        )
    ) {

    }
}