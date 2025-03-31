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
import coil.compose.rememberImagePainter
import com.saibabui.ui.PrimaryButton

@Composable
fun ProfileScreen(
    navController: NavController
) {
    AccountScreen(navController)
}


@Composable
fun ProfileSection(viewModel: AccountViewModel) {
    val userProfile by viewModel.userProfile.collectAsState()
    var isEditing by remember { mutableStateOf(false) }

    UserDetailedCard(
        cardTitle = "Name and Details",
        cardDescription = "Name: ${userProfile.name} \n" +
                "Email: ${userProfile.email} \n" +
                "Phone ${userProfile.phone} \n" +
                "LinkedIn: ${userProfile.linkedin} \n" +
                "Website: ${userProfile.website}"
    )
    UserDetailedCard(
        cardTitle = "Summary",
        cardDescription = userProfile.summary
    )
    UserDetailedCard(
        cardTitle = "Experience",
        cardDescription = userProfile.experience.joinToString(separator = "\n") {
            "${it.title} at ${it.company} (${it.duration}) - ${it.description}"
        }
    )
    UserDetailedCard(
        cardTitle = "Projects",
        cardDescription = userProfile.projects.joinToString(separator = "\n") {
            "${it.name} - ${it.description} - ${it.technologies}"
        }
    )
    UserDetailedCard(
        cardTitle = "Internships",
        cardDescription = userProfile.internships.joinToString(separator = "\n") {
            "${it.title} at ${it.company} (${it.duration}) - ${it.description}"
        }
    )
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
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )
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
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = linkedin,
            onValueChange = { linkedin = it },
            label = { Text("LinkedIn Profile") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = website,
            onValueChange = { website = it },
            label = { Text("Personal Website") },
            modifier = Modifier.fillMaxWidth()
        )
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
        Text(
            text = "Experience (each line: title|company|duration|description)",
            style = MaterialTheme.typography.labelSmall
        )
        TextField(
            value = experienceText,
            onValueChange = { experienceText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Projects
        Text(
            text = "Projects (each line: name|description|technologies)",
            style = MaterialTheme.typography.labelSmall
        )
        TextField(
            value = projectsText,
            onValueChange = { projectsText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Internships
        Text(
            text = "Internships (each line: title|company|duration|description)",
            style = MaterialTheme.typography.labelSmall
        )
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
            val updatedExperience =
                experienceText.split("\n").filter { it.isNotBlank() }.map { line ->
                    val parts = line.split("|")
                    Experience(parts[0], parts[1], parts[2], parts[3])
                }
            val updatedProjects = projectsText.split("\n").filter { it.isNotBlank() }.map { line ->
                val parts = line.split("|")
                Project(parts[0], parts[1], parts[2])
            }
            val updatedInternships =
                internshipsText.split("\n").filter { it.isNotBlank() }.map { line ->
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

@Composable
fun LogoutButton(viewModel: AccountViewModel, navController: NavController) {
    PrimaryButton(
        onClick = {
            viewModel.logout()
            navController.navigate("login") {
                popUpTo("account") { inclusive = true }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        buttonText = "Logout"
    )
}

@Composable
fun AccountScreen(navController: NavController, viewModel: AccountViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Enable scrolling here
    ) {
        ProfileSection(viewModel)
        SettingsSection(viewModel)
        LogoutButton(viewModel, navController)
    }
}