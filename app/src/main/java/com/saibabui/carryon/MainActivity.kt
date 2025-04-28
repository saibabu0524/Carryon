package com.saibabui.carryon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.saibabui.carryon.navigation.RootNavigationGraph
import com.saibabui.carryon.navigation.navigateToHome
import com.saibabui.carryon.ui.theme.Black
import com.saibabui.carryon.ui.theme.CarryonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LoginScreen()
//            CarryonTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                ) {
//                    RootNavigationGraph(navController){
//                        navController.navigateToHome()
//                    }
//                }
//            }
        }
    }
}



@Composable
fun LoginScreen() {
    CarryonTheme {
        Scaffold(
//            backgroundColor = White,
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 24.dp), // Horizontal padding from edges
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Header Section
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Gray)
                            .border(1.dp, Gray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Placeholder for icon (replace with actual logo if available)
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(White, CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Resume Builder",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Form
                    Text(
                        text = "Log in to your account",
                        fontSize = 16.sp,
                        color = Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Email Field
                    val emailState = remember { mutableStateOf("") }
                    TextField(
                        value = emailState.value,
                        onValueChange = { emailState.value = it },
                        label = { Text("Email") },
                        placeholder = { Text("your@email.com", color = LightGray) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors().copy(
                            focusedIndicatorColor = Gray,
                            unfocusedIndicatorColor = Gray,
                            cursorColor = Black,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    val passwordState = remember { mutableStateOf("") }
                    TextField(
                        value = passwordState.value,
                        onValueChange = { passwordState.value = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors().copy(
                            focusedIndicatorColor = Gray,
                            unfocusedIndicatorColor = Gray,
                            cursorColor = Black,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Button
                    Button(
                        onClick = { /* Handle login */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Black),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Log in",
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // Forgot Password Link
                    Text(
                        text = "Forgot password?",
                        fontSize = 14.sp,
                        color = Black,
                        modifier = Modifier.clickable { /* Handle click */ },
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Social Login Buttons
                    SocialLoginButton(
                        text = "Continue with Google",
                        iconRes = android.R.drawable.ic_menu_search // Replace with Google logo
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    SocialLoginButton(
                        text = "Continue with LinkedIn",
                        iconRes = android.R.drawable.ic_menu_info_details // Replace with LinkedIn logo
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Biometric Login Option
                    BiometricLoginButton()
                    Spacer(modifier = Modifier.height(24.dp))

                    // Sign-Up Prompt
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            fontSize = 14.sp,
                            color = Black
                        )
                        Text(
                            text = "Sign up",
                            fontSize = 14.sp,
                            color = Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { /* Handle sign-up */ }
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun SocialLoginButton(text: String, iconRes: Int) {
    Button(
        onClick = { /* Handle social login */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = White),
        border = BorderStroke(1.dp, Gray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified // Use original icon colors if applicable
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                fontSize = 14.sp,
                color = Black
            )
        }
    }
}

@Composable
fun BiometricLoginButton() {
    Button(
        onClick = { /* Handle biometric login */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = White),
        border = BorderStroke(1.dp, Gray),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ThumbUp,
                contentDescription = "Biometric Login",
                modifier = Modifier.size(24.dp),
                tint = Gray
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Use Biometric Login",
                fontSize = 14.sp,
                color = Black
            )
        }
    }
}