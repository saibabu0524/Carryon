package com.saibabui.main.presentation.ui.resumedetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saibabui.ui.CustomLabelForTextField
import com.saibabui.ui.CustomTextFieldState
import com.saibabui.ui.CustomeTextField
import kotlinx.coroutines.flow.MutableStateFlow

// Data classes for resume information
data class ResumeData(
    val personalInfo: PersonalInfo = PersonalInfo(),
    val experience: List<Experience> = emptyList(),
    val education: List<Education> = emptyList(),
    val skills: List<String> = emptyList()
)

data class PersonalInfo(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val linkedin: String = "",
    val summary: String = ""
)

data class Experience(
    val jobTitle: String = "",
    val company: String = "",
    val location: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val description: String = ""
)

data class Education(
    val degree: String = "",
    val institution: String = "",
    val graduationYear: String = "",
    val gpa: String = ""
)

// State class for text fields
data class CustomeTextFieldState(
    val value: String = "",
    val error: String = ""
)



// Main Resume Form Composable
@Composable
fun ResumeForm(
    onSaveResume: (ResumeData) -> Unit
) {
    var currentSection by remember { mutableStateOf(0) }
    val sections = listOf("Personal Info", "Experience", "Education", "Skills")
    val  viewModel = hiltViewModel<ResumeFormViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Section Tabs
        TabRow(
            selectedTabIndex = currentSection,
            modifier = Modifier.fillMaxWidth()
        ) {
            sections.forEachIndexed { index, title ->
                Tab(
                    selected = currentSection == index,
                    onClick = { currentSection = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on selected section
        when (currentSection) {
            0 -> PersonalInfoSection(viewModel)
            1 -> ExperienceSection(viewModel)
            2 -> EducationSection(viewModel)
            3 -> SkillsSection(viewModel)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button
        Button(
            onClick = {
                // Collect all data and save
                val resumeData = collectResumeData(viewModel)
                onSaveResume(resumeData)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Save Resume",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PersonalInfoSection(viewModel: ResumeFormViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Personal Information",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            CustomeTextField(
                supportingText = "Full Name",
                internalState = viewModel.nameState,
                hint = "Enter your full name",
                onValueChange = viewModel::updateName
            )
        }

        item {
            CustomeTextField(
                supportingText = "Email Address",
                internalState = viewModel.emailState,
                keyboardType = KeyboardType.Email,
                hint = "Enter your email",
                onValueChange = viewModel::updateEmail
            )
        }

        item {
            CustomeTextField(
                supportingText = "Phone Number",
                internalState = viewModel.phoneState,
                keyboardType = KeyboardType.Phone,
                hint = "Enter your phone number",
                onValueChange = viewModel::updatePhone
            )
        }

        item {
            CustomeTextField(
                supportingText = "Address",
                internalState = viewModel.addressState,
                hint = "Enter your address",
                onValueChange = viewModel::updateAddress
            )
        }

        item {
            CustomeTextField(
                supportingText = "LinkedIn Profile (Optional)",
                internalState = viewModel.linkedinState,
                hint = "Enter LinkedIn URL",
                onValueChange = viewModel::updateLinkedin
            )
        }

        item {
            CustomMultiLineTextField(
                supportingText = "Professional Summary",
                internalState = viewModel.summaryState,
                hint = "Brief description of your professional background",
                onValueChange = viewModel::updateSummary
            )
        }
    }
}

@Composable
fun ExperienceSection(viewModel: ResumeFormViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Work Experience",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { /* Add new experience */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Experience")
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomeTextField(
                        supportingText = "Job Title",
                        internalState = viewModel.jobTitleState,
                        hint = "e.g. Software Developer",
                        onValueChange = { /* Update job title */ }
                    )

                    CustomeTextField(
                        supportingText = "Company",
                        internalState = viewModel.companyState,
                        hint = "Company name",
                        onValueChange = { /* Update company */ }
                    )

                    CustomeTextField(
                        supportingText = "Location",
                        internalState = viewModel.locationState,
                        hint = "City, State",
                        onValueChange = { /* Update location */ }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CustomeTextField(
                            supportingText = "Start Date",
                            internalState = viewModel.startDateState,
                            hint = "MM/YYYY",
                            modifier = Modifier.weight(1f),
                            onValueChange = { /* Update start date */ }
                        )

                        CustomeTextField(
                            supportingText = "End Date",
                            internalState = viewModel.endDateState,
                            hint = "MM/YYYY or Present",
                            modifier = Modifier.weight(1f),
                            onValueChange = { /* Update end date */ }
                        )
                    }

                    CustomMultiLineTextField(
                        supportingText = "Job Description",
                        internalState = viewModel.descriptionState,
                        hint = "Describe your responsibilities and achievements",
                        onValueChange = { /* Update description */ }
                    )
                }
            }
        }
    }
}

@Composable
fun EducationSection(viewModel: ResumeFormViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Education",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { /* Add new education */ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Education")
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomeTextField(
                        supportingText = "Degree",
                        internalState = viewModel.degreeState,
                        hint = "e.g. Bachelor of Computer Science",
                        onValueChange = { /* Update degree */ }
                    )

                    CustomeTextField(
                        supportingText = "Institution",
                        internalState = viewModel.institutionState,
                        hint = "University/College name",
                        onValueChange = { /* Update institution */ }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CustomeTextField(
                            supportingText = "Graduation Year",
                            internalState = viewModel.graduationYearState,
                            keyboardType = KeyboardType.Number,
                            hint = "YYYY",
                            modifier = Modifier.weight(1f),
                            onValueChange = { /* Update graduation year */ }
                        )

                        CustomeTextField(
                            supportingText = "GPA (Optional)",
                            internalState = viewModel.gpaState,
                            keyboardType = KeyboardType.Decimal,
                            hint = "3.5",
                            modifier = Modifier.weight(1f),
                            onValueChange = { /* Update GPA */ }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SkillsSection(viewModel: ResumeFormViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Skills",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            CustomMultiLineTextField(
                supportingText = "Technical Skills",
                internalState = viewModel.skillsState,
                hint = "Separate skills with commas (e.g. Android, Kotlin, Java, React)",
                onValueChange = { /* Update skills */ }
            )
        }
    }
}

// Multi-line version of your custom text field
@Composable
fun CustomMultiLineTextField(
    supportingText: String,
    internalState: MutableStateFlow<CustomTextFieldState>,
    keyboardType: KeyboardType = KeyboardType.Text,
    hint: String = "Enter text",
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    val state = internalState.collectAsState().value
    Column(modifier = modifier) {
        CustomLabelForTextField(
            supportingText,
            if (state.error.isEmpty()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
        OutlinedTextField(
            value = state.value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedIndicatorColor = LightGray,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedIndicatorColor = LightGray,
            ),
            modifier = Modifier.fillMaxWidth(),
            isError = state.error.isNotEmpty(),
            supportingText = {
                if (state.error.isNotEmpty()) {
                    Text(
                        text = state.error,
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            minLines = 3,
            maxLines = 6,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            placeholder = {
                Text(
                    text = hint,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            },
            shape = RoundedCornerShape(8.dp),
        )
    }
}

// Helper function to collect all resume data
private fun collectResumeData(viewModel: ResumeFormViewModel): ResumeData {
    return ResumeData(
        personalInfo = PersonalInfo(
            name = viewModel.nameState.value.value,
            email = viewModel.emailState.value.value,
            phone = viewModel.phoneState.value.value,
            address = viewModel.addressState.value.value,
            linkedin = viewModel.linkedinState.value.value,
            summary = viewModel.summaryState.value.value
        )
        // Add experience, education, and skills collection here
    )
}

// Usage in your Activity/Fragment
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResumeGeneratorApp() {
    ResumeForm { resumeData ->
        // Handle the collected resume data
        // You can save it to database, generate PDF, etc.
        println("Resume Data: $resumeData")
    }
}