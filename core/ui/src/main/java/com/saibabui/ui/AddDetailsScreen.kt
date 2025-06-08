package com.saibabui.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDetailsScreen() {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    // Lists for dynamic education and work entries
    var educationEntries by remember { mutableStateOf(listOf(EducationEntry())) }
    var workEntries by remember { mutableStateOf(listOf(WorkEntry())) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Details") },
                navigationIcon = {
                    IconButton(onClick = { /* Back action */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { /* Save action */ }) {
                        Text("Save")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { /* Attach file */ }) {
                    Icon(Icons.Default.AttachFile, contentDescription = "Attach")
                }
                IconButton(onClick = { /* Add section */ }) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add")
                }
                IconButton(onClick = { /* Remove section */ }) {
                    Icon(Icons.Default.RemoveCircle, contentDescription = "Remove")
                }
                Button(onClick = { /* Download action */ }) {
                    Text("Download")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Personal Information Section
            SectionHeader(title = "PERSONAL INFORMATION")
            ReusableTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Enter your full name"
            )
            ReusableTextField(
                value = email,
                onValueChange = { email = it },
                label = "Enter your email",
                keyboardType = KeyboardType.Email
            )
            ReusableTextField(
                value = phone,
                onValueChange = { phone = it },
                label = "Enter your phone number",
                keyboardType = KeyboardType.Phone
            )

            // Education Section
            SectionHeader(
                title = "EDUCATION",
                onAddClick = { educationEntries = educationEntries + EducationEntry() }
            )
            educationEntries.forEachIndexed { index, entry ->
                EducationForm(
                    entry = entry,
                    onUpdate = { updatedEntry ->
                        educationEntries = educationEntries.toMutableList().apply {
                            this[index] = updatedEntry
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Work Experience Section
            SectionHeader(
                title = "WORK EXPERIENCE",
                onAddClick = { workEntries = workEntries + WorkEntry() }
            )
            workEntries.forEachIndexed { index, entry ->
                WorkExperienceForm(
                    entry = entry,
                    onUpdate = { updatedEntry ->
                        workEntries = workEntries.toMutableList().apply {
                            this[index] = updatedEntry
                        }
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Reusable Components

@Composable
fun ReusableTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        maxLines = maxLines
    )
}

@Composable
fun ReusableDateField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text("dd/mm/yyyy") },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
        }
    )
}

@Composable
fun SectionHeader(
    title: String,
    onAddClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 16.sp
        )
        onAddClick?.let {
            IconButton(onClick = it) {
                Icon(Icons.Default.Add, contentDescription = "Add $title")
            }
        }
    }
}

// Data Classes and Forms

data class EducationEntry(
    var institution: String = "",
    var degree: String = "",
    var startDate: String = "",
    var endDate: String = ""
)

data class WorkEntry(
    var company: String = "",
    var position: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var description: String = ""
)

@Composable
fun EducationForm(entry: EducationEntry, onUpdate: (EducationEntry) -> Unit) {
    Column {
        ReusableTextField(
            value = entry.institution,
            onValueChange = { onUpdate(entry.copy(institution = it)) },
            label = "Enter institution name"
        )
        ReusableTextField(
            value = entry.degree,
            onValueChange = { onUpdate(entry.copy(degree = it)) },
            label = "Enter your degree"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ReusableDateField(
                value = entry.startDate,
                onValueChange = { onUpdate(entry.copy(startDate = it)) },
                label = "Start Date",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ReusableDateField(
                value = entry.endDate,
                onValueChange = { onUpdate(entry.copy(endDate = it)) },
                label = "End Date",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun WorkExperienceForm(entry: WorkEntry, onUpdate: (WorkEntry) -> Unit) {
    Column {
        ReusableTextField(
            value = entry.company,
            onValueChange = { onUpdate(entry.copy(company = it)) },
            label = "Enter company name"
        )
        ReusableTextField(
            value = entry.position,
            onValueChange = { onUpdate(entry.copy(position = it)) },
            label = "Enter your position"
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ReusableDateField(
                value = entry.startDate,
                onValueChange = { onUpdate(entry.copy(startDate = it)) },
                label = "Start Date",
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ReusableDateField(
                value = entry.endDate,
                onValueChange = { onUpdate(entry.copy(endDate = it)) },
                label = "End Date",
                modifier = Modifier.weight(1f)
            )
        }
        ReusableTextField(
            value = entry.description,
            onValueChange = { onUpdate(entry.copy(description = it)) },
            label = "Describe your responsibilities",
            maxLines = 4,
            modifier = Modifier.height(100.dp)
        )
    }
}