package com.saibabui.main.presentation.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

//@Composable
//fun HomeScreen(
//    navController: NavController,
//    paddingValues: PaddingValues
//) {
//    HomeScreen(paddingValues)
//}
//
//
//@Composable
//fun HomeScreen(
//    paddingValues: PaddingValues
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(paddingValues = paddingValues),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = "Home Screen")
//    }
//}



// Resume Card Composable
@Composable
fun ResumeCard(resume: Resume, onEditClick: () -> Unit, onViewClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
//        elevation = 4.dp
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = resume.name, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "Last modified: ${resume.lastModified}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = onEditClick) { Text("Edit") }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onViewClick) { Text("View") }
            }
        }
    }
}

// Resume Tip Card Composable
@Composable
fun ResumeTipCard(tip: Tip) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
//        elevation = 4.dp
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

// Template Card Composable
@Composable
fun TemplateCard(template: Template, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .padding(end = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Placeholder icon; replace with actual image loading (e.g., Coil)
            Icon(
                imageVector = Icons.Default.Description,
                contentDescription = template.name,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = template.name, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

// Home Screen Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val userName by viewModel.userName.collectAsState()
    val recentResumes by viewModel.recentResumes.collectAsState()
    val resumeTips by viewModel.resumeTips.collectAsState()
    val featuredTemplates by viewModel.featuredTemplates.collectAsState()

    // Select a random tip, persisting across recompositions
    val randomTip = remember { resumeTips.randomOrNull() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Resume Maker") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("templates") }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create New Resume"
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Welcome Message
            item {
                Text(
                    text = "Welcome, $userName",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Recent Resumes Section
            if (recentResumes.isNotEmpty()) {
                item {
                    Text(
                        text = "Recent Resumes",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(recentResumes.take(3)) { resume ->
                    ResumeCard(
                        resume = resume,
                        onEditClick = { navController.navigate("edit_resume/${resume.id}") },
                        onViewClick = { navController.navigate("view_resume/${resume.id}") }
                    )
                }
            } else {
                item {
                    Text(
                        text = "No resumes yet. Create your first resume!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // View All Resumes Button
            item {
                Button(
                    onClick = { navController.navigate("resumes") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View All Resumes")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Resume Tips Section
            randomTip?.let { tip ->
                item {
                    ResumeTipCard(tip)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Featured Templates Carousel
            item {
                Text(
                    text = "Featured Templates",
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                LazyRow {
                    items(featuredTemplates) { template ->
                        TemplateCard(
                            template = template,
                            onClick = {
                                // Simulate creating a new resume with the selected template
                                val newResumeId = "new_${template.id}" // Replace with actual logic
                                navController.navigate("personal_info/$newResumeId")
                            }
                        )
                    }
                }
            }
        }
    }
}