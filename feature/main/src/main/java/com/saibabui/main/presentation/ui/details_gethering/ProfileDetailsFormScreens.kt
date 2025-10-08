package com.saibabui.main.presentation.ui.details_gethering

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.saibabui.network.home.model.UserProfileCreate

/**
 * Profile Details Form Screens for gathering user profile information after login
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailsFormScreens(
    navController: NavController,
    viewModel: UserProfileFormViewModel = hiltViewModel()
) {
    var currentStep by remember { mutableStateOf(0) }
    val steps = listOf(
        "Personal Information",
        "Contact & Social",
        "Professional Details",
        "Review & Submit"
    )

    val profileData = remember {
        mutableStateOf(
            UserProfileCreate(
                fullName = "",
                email = "",
                phone = "",
                location = "",
                userId = ""
            )
        )
    }

    // Observe UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val profileDetails by viewModel.profileDetails.collectAsState()

    LaunchedEffect(Unit) {
        // Initialize by fetching profile details on first load
        viewModel.fetchProfileDetails()
    }

    // Handle UI state changes (show snackbar messages, navigate on success, etc.)
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    snackbarMessage?.let { message ->
        // Show a simple Snackbar - in a real app you might use a proper snackbar component
        // For now, we'll just clear it after a short delay
        LaunchedEffect(message) {
            // In a real app, you'd want to show a proper snackbar here
            kotlinx.coroutines.delay(3000) // 3 seconds
            snackbarMessage = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile Setup - Step ${currentStep + 1}/${steps.size}",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentStep > 0) {
                            currentStep--
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            // Show loading indicator if needed
            if (uiState is ProfileFormUiState.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Progress indicator
            LinearProgressIndicator(
                progress = (currentStep + 1f) / steps.size,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Handle state changes
            var isSuccess by remember { mutableStateOf(false) }
            when (val state = uiState) {
                is ProfileFormUiState.Success -> {
                    if (!isSuccess) {
                        isSuccess = true
                        LaunchedEffect(state.message) {
                            // Show success message
                            snackbarMessage = state.message
                            // Navigate back to profile screen after a short delay
                            kotlinx.coroutines.delay(2000)
                            navController.popBackStack()
                        }
                    }
                }
                is ProfileFormUiState.Error -> {
                    snackbarMessage = state.message
                }
                else -> {}
            }

            when (currentStep) {
                0 -> PersonalInfoStep(
                    profileData = profileData,
                    onNext = { currentStep++ }
                )
                1 -> ContactSocialStep(
                    profileData = profileData,
                    onNext = { currentStep++ },
                    onPrevious = { currentStep-- }
                )
                2 -> ProfessionalDetailsStep(
                    profileData = profileData,
                    onNext = { currentStep++ },
                    onPrevious = { currentStep-- }
                )
                3 -> ReviewSubmitStep(
                    profileData = profileData,
                    onSubmit = {
                        // Handle profile creation/update
                        viewModel.determineActionAndExecute(profileData.value)
                    },
                    onPrevious = { currentStep-- }
                )
            }
        }
    }
}

@Composable
private fun PersonalInfoStep(
    profileData: MutableState<UserProfileCreate>,
    onNext: () -> Unit
) {
    var fullName by remember { mutableStateOf(profileData.value.fullName) }
    var location by remember { mutableStateOf(profileData.value.location) }
    var dateOfBirth by remember { mutableStateOf(profileData.value.dateOfBirth ?: "") }
    var nationality by remember { mutableStateOf(profileData.value.nationality ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Personal Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                profileData.value = profileData.value.copy(fullName = it)
            },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Enter your full name as it should appear on your profile") }
        )

        OutlinedTextField(
            value = location,
            onValueChange = {
                location = it
                profileData.value = profileData.value.copy(location = it)
            },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("City, Country") }
        )

        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = {
                dateOfBirth = it
                profileData.value = profileData.value.copy(dateOfBirth = it)
            },
            label = { Text("Date of Birth") },
            placeholder = { Text("YYYY-MM-DD") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nationality,
            onValueChange = {
                nationality = it
                profileData.value = profileData.value.copy(nationality = it)
            },
            label = { Text("Nationality") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Basic validation
                if (fullName.isNotBlank() && location.isNotBlank()) {
                    onNext()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Next")
            Icon(Icons.Filled.Check, contentDescription = "Next", modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
private fun ContactSocialStep(
    profileData: MutableState<UserProfileCreate>,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    var email by remember { mutableStateOf(profileData.value.email) }
    var phone by remember { mutableStateOf(profileData.value.phone) }
    var linkedinUrl by remember { mutableStateOf(profileData.value.linkedinUrl ?: "") }
    var githubUrl by remember { mutableStateOf(profileData.value.githubUrl ?: "") }
    var portfolioUrl by remember { mutableStateOf(profileData.value.portfolioUrl ?: "") }
    var twitterUrl by remember { mutableStateOf(profileData.value.twitterUrl ?: "") }
    var customWebsite by remember { mutableStateOf(profileData.value.customWebsite ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Contact & Social Links",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                profileData.value = profileData.value.copy(email = it)
            },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                profileData.value = profileData.value.copy(phone = it)
            },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = linkedinUrl,
            onValueChange = {
                linkedinUrl = it
                profileData.value = profileData.value.copy(linkedinUrl = it)
            },
            label = { Text("LinkedIn URL") },
            placeholder = { Text("https://linkedin.com/in/username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = githubUrl,
            onValueChange = {
                githubUrl = it
                profileData.value = profileData.value.copy(githubUrl = it)
            },
            label = { Text("GitHub URL") },
            placeholder = { Text("https://github.com/username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = portfolioUrl,
            onValueChange = {
                portfolioUrl = it
                profileData.value = profileData.value.copy(portfolioUrl = it)
            },
            label = { Text("Portfolio URL") },
            placeholder = { Text("https://yourportfolio.com") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = twitterUrl,
            onValueChange = {
                twitterUrl = it
                profileData.value = profileData.value.copy(twitterUrl = it)
            },
            label = { Text("Twitter URL") },
            placeholder = { Text("https://twitter.com/username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = customWebsite,
            onValueChange = {
                customWebsite = it
                profileData.value = profileData.value.copy(customWebsite = it)
            },
            label = { Text("Personal Website") },
            placeholder = { Text("https://yoursite.com") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.weight(1f)
            ) {
                Text("Previous")
            }
            Button(
                onClick = {
                    // Basic validation
                    if (email.isNotBlank() && phone.isNotBlank()) {
                        onNext()
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
                Icon(Icons.Filled.Check, contentDescription = "Next", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun ProfessionalDetailsStep(
    profileData: MutableState<UserProfileCreate>,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    var professionalSummary by remember { mutableStateOf(profileData.value.professionalSummary ?: "") }
    var headline by remember { mutableStateOf(profileData.value.headline ?: "") }
    var avatarUrl by remember { mutableStateOf(profileData.value.avatarUrl ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Professional Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = headline,
            onValueChange = {
                headline = it
                profileData.value = profileData.value.copy(headline = it)
            },
            label = { Text("Professional Headline") },
            placeholder = { Text("e.g. Senior Software Engineer") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = professionalSummary,
            onValueChange = {
                professionalSummary = it
                profileData.value = profileData.value.copy(professionalSummary = it)
            },
            label = { Text("Professional Summary") },
            placeholder = { Text("Brief description of your professional background") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            supportingText = { Text("Tell us about your professional experience and skills") }
        )

        OutlinedTextField(
            value = avatarUrl,
            onValueChange = {
                avatarUrl = it
                profileData.value = profileData.value.copy(avatarUrl = it)
            },
            label = { Text("Profile Picture URL") },
            placeholder = { Text("https://example.com/profile.jpg") },
            modifier = Modifier.fillMaxWidth(),
            supportingText = { Text("Optional: URL to your profile picture") }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.weight(1f)
            ) {
                Text("Previous")
            }
            Button(
                onClick = onNext,
                modifier = Modifier.weight(1f)
            ) {
                Text("Review")
                Icon(Icons.Filled.Check, contentDescription = "Review", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun ReviewSubmitStep(
    profileData: MutableState<UserProfileCreate>,
    onSubmit: () -> Unit,
    onPrevious: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Review Your Profile",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Personal Information", style = MaterialTheme.typography.titleMedium)
                Text("Full Name: ${profileData.value.fullName}")
                Text("Location: ${profileData.value.location}")
                profileData.value.dateOfBirth?.let { Text("Date of Birth: $it") }
                profileData.value.nationality?.let { Text("Nationality: $it") }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Contact & Social", style = MaterialTheme.typography.titleMedium)
                Text("Email: ${profileData.value.email}")
                Text("Phone: ${profileData.value.phone}")
                profileData.value.linkedinUrl?.takeIf { it.isNotEmpty() }?.let { Text("LinkedIn: $it") }
                profileData.value.githubUrl?.takeIf { it.isNotEmpty() }?.let { Text("GitHub: $it") }
                profileData.value.portfolioUrl?.takeIf { it.isNotEmpty() }?.let { Text("Portfolio: $it") }
                profileData.value.twitterUrl?.takeIf { it.isNotEmpty() }?.let { Text("Twitter: $it") }
                profileData.value.customWebsite?.takeIf { it.isNotEmpty() }?.let { Text("Website: $it") }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Professional Details", style = MaterialTheme.typography.titleMedium)
                profileData.value.headline?.takeIf { it.isNotEmpty() }?.let { Text("Headline: $it") }
                profileData.value.professionalSummary?.takeIf { it.isNotEmpty() }?.let { Text("Summary: $it") }
                profileData.value.avatarUrl?.takeIf { it.isNotEmpty() }?.let { Text("Avatar URL: $it") }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.weight(1f)
            ) {
                Text("Edit")
            }
            Button(
                onClick = onSubmit,
                modifier = Modifier.weight(1f)
            ) {
                Text("Submit Profile")
                Icon(Icons.Filled.Check, contentDescription = "Submit", modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}