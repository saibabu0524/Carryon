package com.saibabui.main.presentation.ui.components.resume

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.saibabui.network.home.model.ResumeResponse

@Composable
fun CreateEditResumeScreen(
    resume: ResumeResponse? = null,
    onSave: (String, String?) -> Unit, // (title, templateId) -> Unit
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf(resume?.title ?: "") }
    var selectedTemplate by remember { mutableStateOf(resume?.templateId) }
    var useAiGeneration by remember { mutableStateOf(false) }
    var showTemplateSelector by remember { mutableStateOf(false) }
    
    val isValid = title.isNotBlank()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top app bar
        TopAppBar(
            title = {
                Text(
                    text = if (resume == null) "Create Resume" else "Edit Resume",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = onCancel) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                if (resume != null) {
                    IconButton(onClick = { /* Handle delete */ }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        )
        
        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Resume title section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Resume Details",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Resume Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = title.isBlank() && title.isNotEmpty(), // Show error after user tries to input
                        supportingText = {
                            if (title.isBlank() && title.isNotEmpty()) {
                                Text("Title is required")
                            }
                        }
                    )
                }
            }
            
            // Template selection section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Template",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    
                    // Selected template preview or selector
                    if (selectedTemplate != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Selected Template",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                
                                Text(
                                    text = selectedTemplate ?: "Default",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                            
                            TextButton(
                                onClick = { showTemplateSelector = true }
                            ) {
                                Text("Change")
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = "No template",
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = "No template selected",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Button(
                                onClick = { showTemplateSelector = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Choose Template")
                            }
                        }
                    }
                }
            }
            
            // AI Generation section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "AI Assistance",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Generate with AI",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            
                            Text(
                                text = "Let AI help you create content based on your profile",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        Switch(
                            checked = useAiGeneration,
                            onCheckedChange = { useAiGeneration = it }
                        )
                    }
                }
            }
            
            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                
                Button(
                    onClick = { onSave(title, selectedTemplate) },
                    modifier = Modifier.weight(1f),
                    enabled = isValid
                ) {
                    Text(if (resume == null) "Create" else "Save")
                }
            }
        }
    }
    
    // Template selector dialog
    if (showTemplateSelector) {
        TemplateSelectorDialog(
            onTemplateSelected = { templateId ->
                selectedTemplate = templateId
                showTemplateSelector = false
            },
            onDismiss = { showTemplateSelector = false }
        )
    }
}

@Composable
private fun TemplateSelectorDialog(
    onTemplateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Choose Template",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            // In a real implementation, this would show a grid of templates
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Sample templates - in reality this would come from a repository
                val templates = listOf(
                    "modern_professional" to "Modern Professional",
                    "creative_portfolio" to "Creative Portfolio",
                    "simple_classic" to "Simple Classic",
                    "technical_resume" to "Technical Resume",
                    "executive_cv" to "Executive CV"
                )
                
                templates.forEach { (id, name) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        onClick = { onTemplateSelected(id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            
                            Text(
                                text = name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}