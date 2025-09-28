package com.saibabui.main.presentation.ui.components.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.saibabui.network.home.model.ProfileResponse
import com.saibabui.network.home.model.ProfileUpdateRequest

@Composable
fun ProfileForm(
    profile: ProfileResponse?,
    isEditing: Boolean,
    onProfileUpdate: (ProfileUpdateRequest) -> Unit,
    modifier: Modifier = Modifier
) {
    var firstName by remember { mutableStateOf(profile?.firstName ?: "") }
    var lastName by remember { mutableStateOf(profile?.lastName ?: "") }
//    var email by remember { mutableStateOf(profile?.e ?: "") }
    var phone by remember { mutableStateOf(profile?.phone ?: "") }
    var address by remember { mutableStateOf(profile?.address ?: "") }
    var city by remember { mutableStateOf(profile?.city ?: "") }
    var state by remember { mutableStateOf(profile?.state ?: "") }
    var country by remember { mutableStateOf(profile?.country ?: "") }
    var postalCode by remember { mutableStateOf(profile?.postalCode ?: "") }
    var bio by remember { mutableStateOf(profile?.bio ?: "") }
    var linkedinUrl by remember { mutableStateOf(profile?.linkedinUrl ?: "") }
    var githubUrl by remember { mutableStateOf(profile?.githubUrl ?: "") }
    var portfolioUrl by remember { mutableStateOf(profile?.portfolioUrl ?: "") }
    var isPublic by remember { mutableStateOf(profile?.isPublic ?: false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Personal Information Section
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
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )

//                OutlinedTextField(
//                    value = email,
//                    onValueChange = { email = it },
//                    label = { Text("Email") },
//                    modifier = Modifier.fillMaxWidth(),
//                    enabled = false, // Email usually can't be changed
//                    singleLine = true,
////                    keyboardType = KeyboardType.Email
//                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true,
//                    keyboardType = KeyboardType.Phone
                )
            }
        }

        // Address Section
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
                    text = "Address",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("City") },
                        modifier = Modifier.weight(1f),
                        enabled = isEditing,
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = state,
                        onValueChange = { state = it },
                        label = { Text("State") },
                        modifier = Modifier.weight(1f),
                        enabled = isEditing,
                        singleLine = true
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it },
                        label = { Text("Country") },
                        modifier = Modifier.weight(1f),
                        enabled = isEditing,
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = postalCode,
                        onValueChange = { postalCode = it },
                        label = { Text("Postal Code") },
                        modifier = Modifier.weight(1f),
                        enabled = isEditing,
                        singleLine = true
                    )
                }
            }
        }

        // About Section
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
                    text = "About",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    enabled = isEditing,
                    maxLines = 5
                )
            }
        }

        // Social Links Section
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
                    text = "Social Links",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                OutlinedTextField(
                    value = linkedinUrl,
                    onValueChange = { linkedinUrl = it },
                    label = { Text("LinkedIn URL") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )

                OutlinedTextField(
                    value = githubUrl,
                    onValueChange = { githubUrl = it },
                    label = { Text("GitHub URL") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )

                OutlinedTextField(
                    value = portfolioUrl,
                    onValueChange = { portfolioUrl = it },
                    label = { Text("Portfolio URL") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isEditing,
                    singleLine = true
                )
            }
        }

        // Privacy Section
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
                    text = "Privacy",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Make profile public")
                    Switch(
                        checked = isPublic,
                        onCheckedChange = { isPublic = it },
                        enabled = isEditing
                    )
                }
            }
        }

        // Save Button (when in editing mode)
        if (isEditing) {
            Button(
                onClick = {
                    val updateRequest = ProfileUpdateRequest(
                        firstName = firstName.ifBlank { null },
                        lastName = lastName.ifBlank { null },
                        phone = phone.ifBlank { null },
                        address = address.ifBlank { null },
                        city = city.ifBlank { null },
                        state = state.ifBlank { null },
                        country = country.ifBlank { null },
                        postalCode = postalCode.ifBlank { null },
                        bio = bio.ifBlank { null },
                        linkedinUrl = linkedinUrl.ifBlank { null },
                        githubUrl = githubUrl.ifBlank { null },
                        portfolioUrl = portfolioUrl.ifBlank { null },
                        isPublic = isPublic
                    )
                    onProfileUpdate(updateRequest)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = firstName.isNotBlank() || lastName.isNotBlank()
            ) {
                Text("Save Changes")
            }
        }
    }
}