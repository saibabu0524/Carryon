package com.saibabui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumePreviewScreen() {
    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        },
        topBar = {
            TopAppBar(
                title = { Text("Resume Preview") },
                navigationIcon = {
                    IconButton(onClick = { /* Back action */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { /* Edit action */ }) {
                        Text("EDIT", color = Color.Black)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Personal Info
            Text(
                text = "JOHN DOE",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Email",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "john.doe@example.com",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    Icons.Default.Phone,
                    contentDescription = "Phone",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "+1 234 567 8900",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Education Section
            Text(
                text = "Education",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Stanford University",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Master of Computer Science",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "2023 - 2025",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Work Experience Section
            Text(
                text = "Work Experience",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Software Engineer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Google",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "2020 - Present",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Led development of core features for Google Cloud Platform. Managed team of 5 engineers.",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Skills Section
            Text(
                text = "Skills",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SkillChip("React.js")
                SkillChip("TypeScript")
                SkillChip("Node.js")
                SkillChip("Python")
            }
        }
    }
}

@Composable
fun SkillChip(skill: String) {
    Box(
        modifier = Modifier
            .background(Color.LightGray, RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = skill,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { /* Home action */ },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Templates action */ },
            icon = { Icon(Icons.Default.List, contentDescription = "Templates") },
            label = { Text("Templates") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { /* Recent action */ },
            icon = { Icon(Icons.Default.History, contentDescription = "Recent") },
            label = { Text("Recent") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Profile action */ },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}