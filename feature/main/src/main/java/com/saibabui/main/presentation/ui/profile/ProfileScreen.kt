package com.saibabui.main.presentation.ui.profile

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter

@Composable
fun ProfileScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    AccountScreen(navController)
}


@Composable
fun ProfileScreen(
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Profile Screen")
    }
}



@Composable
fun ProfileSection(viewModel: AccountViewModel) {
    val userProfile by viewModel.userProfile.collectAsState()
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) {
        EditProfileForm(userProfile = userProfile, onSave = { updatedProfile ->
            viewModel.updateProfile(updatedProfile)
            isEditing = false
        })
    } else {
        ProfileCard(userProfile = userProfile, onEditClick = { isEditing = true })
    }
}

@Composable
fun ProfileCard(userProfile: UserProfile, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Profile Picture
            Image(
                painter = rememberImagePainter(userProfile.profilePictureUrl),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Basic Details
            Text(text = "Profile Details", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Name: ${userProfile.name}")
            Text(text = "Email: ${userProfile.email}")
            Text(text = "Phone: ${userProfile.phone}")
            Text(text = "Address: ${userProfile.address}")
            Text(text = "LinkedIn: ${userProfile.linkedin}")
            Text(text = "Website: ${userProfile.website}")
            Spacer(modifier = Modifier.height(16.dp))

            // Summary
            Text(text = "Summary", style = MaterialTheme.typography.labelSmall)
            Text(text = userProfile.summary)
            Spacer(modifier = Modifier.height(16.dp))

            // Experience
            Text(text = "Experience", style = MaterialTheme.typography.labelSmall)
            userProfile.experience.forEach { exp ->
                Text(text = "- ${exp.title} at ${exp.company} (${exp.duration})")
                Text(text = "  ${exp.description}")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Projects
            Text(text = "Projects", style = MaterialTheme.typography.labelSmall)
            userProfile.projects.forEach { proj ->
                Text(text = "- ${proj.name}")
                Text(text = "  ${proj.description}")
                Text(text = "  Technologies: ${proj.technologies}")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Internships
            Text(text = "Internships", style = MaterialTheme.typography.labelSmall)
            userProfile.internships.forEach { intern ->
                Text(text = "- ${intern.title} at ${intern.company} (${intern.duration})")
                Text(text = "  ${intern.description}")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Edit Button
            Button(
                onClick = onEditClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Edit Profile")
            }
        }
    }
}

@Composable
fun EditProfileForm(userProfile: UserProfile, onSave: (UserProfile) -> Unit) {
    var name by remember { mutableStateOf(userProfile.name) }
    var email by remember { mutableStateOf(userProfile.email) }
    var phone by remember { mutableStateOf(userProfile.phone) }
    var address by remember { mutableStateOf(userProfile.address) }
    var linkedin by remember { mutableStateOf(userProfile.linkedin) }
    var website by remember { mutableStateOf(userProfile.website) }
    var summary by remember { mutableStateOf(userProfile.summary) }
    // For simplicity, handle experience, projects, and internships as text areas
    var experienceText by remember {
        mutableStateOf(userProfile.experience.joinToString("\n") { "${it.title}|${it.company}|${it.duration}|${it.description}" })
    }
    var projectsText by remember {
        mutableStateOf(userProfile.projects.joinToString("\n") { "${it.name}|${it.description}|${it.technologies}" })
    }
    var internshipsText by remember {
        mutableStateOf(userProfile.internships.joinToString("\n") { "${it.title}|${it.company}|${it.duration}|${it.description}" })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture (non-editable for simplicity)
        Image(
            painter = rememberImagePainter(userProfile.profilePictureUrl),
            contentDescription = "Profile Picture",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Basic Details
        Text(text = "Edit Profile", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(value = name, onValueChange = { name = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = address, onValueChange = { address = it }, label = { Text("Address") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = linkedin, onValueChange = { linkedin = it }, label = { Text("LinkedIn Profile") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = website, onValueChange = { website = it }, label = { Text("Personal Website") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = summary,
            onValueChange = { summary = it },
            label = { Text("Professional Summary") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Experience
        Text(text = "Experience (each line: title|company|duration|description)", style = MaterialTheme.typography.labelSmall)
        TextField(
            value = experienceText,
            onValueChange = { experienceText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Projects
        Text(text = "Projects (each line: name|description|technologies)", style = MaterialTheme.typography.labelSmall)
        TextField(
            value = projectsText,
            onValueChange = { projectsText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Internships
        Text(text = "Internships (each line: title|company|duration|description)", style = MaterialTheme.typography.labelSmall)
        TextField(
            value = internshipsText,
            onValueChange = { internshipsText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(onClick = {
            val updatedExperience = experienceText.split("\n").filter { it.isNotBlank() }.map { line ->
                val parts = line.split("|")
                Experience(parts[0], parts[1], parts[2], parts[3])
            }
            val updatedProjects = projectsText.split("\n").filter { it.isNotBlank() }.map { line ->
                val parts = line.split("|")
                Project(parts[0], parts[1], parts[2])
            }
            val updatedInternships = internshipsText.split("\n").filter { it.isNotBlank() }.map { line ->
                val parts = line.split("|")
                Internship(parts[0], parts[1], parts[2], parts[3])
            }
            val updatedProfile = userProfile.copy(
                name = name,
                email = email,
                phone = phone,
                address = address,
                linkedin = linkedin,
                website = website,
                summary = summary,
                experience = updatedExperience,
                projects = updatedProjects,
                internships = updatedInternships
            )
            onSave(updatedProfile)
        }) {
            Text("Save Changes")
        }
    }
}

// Composable for Settings Toggles Section
@Composable
fun SettingsSection(viewModel: AccountViewModel) {
    val isNotificationEnabled by viewModel.isNotificationEnabled.collectAsState()
    val isDarkModeEnabled by viewModel.isDarkModeEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enable Notifications", modifier = Modifier.weight(1f))
            Switch(
                checked = isNotificationEnabled,
                onCheckedChange = { viewModel.toggleNotification(it) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dark Mode", modifier = Modifier.weight(1f))
            Switch(
                checked = isDarkModeEnabled,
                onCheckedChange = { viewModel.toggleDarkMode(it) }
            )
        }
    }
}

// Composable for Logout Button
@Composable
fun LogoutButton(viewModel: AccountViewModel, navController: NavController) {
    Button(
        onClick = {
            viewModel.logout()
            navController.navigate("login") {
                popUpTo("account") { inclusive = true }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Logout")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(navController: NavController, viewModel: AccountViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Enable scrolling here
        ) {
            ProfileSection(viewModel)
            SettingsSection(viewModel)
            LogoutButton(viewModel, navController)
        }
    }
}