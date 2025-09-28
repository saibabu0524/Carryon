package com.saibabui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PrimaryButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors()
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        colors = buttonColors
    ) {
        Text(
            text = buttonText,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun SecondaryButton(buttonText: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier,
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(
            text = buttonText,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit, 
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        enabled = enabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            // Note: You would typically use the Google logo here
            // For now, using a placeholder icon or text
            Text(
                text = "Sign in with Google",
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Preview
@Composable
fun PrimaryButtonPreview() {
//    PrimaryButton(buttonText = "Primary Button", onClick = {})
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FocusTimerScreen() {
    val selectedDuration = remember { mutableStateOf(0) }
    val isRunning = remember { mutableStateOf(false) }
    val remainingTime = remember { mutableStateOf(0) }
    val customDuration = remember { mutableStateOf("") }

    val timerScale by animateFloatAsState(
        targetValue = if (isRunning.value) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(isRunning.value) {
        if (isRunning.value) {
            while (remainingTime.value > 0) {
                delay(1000)
                remainingTime.value -= 1
            }
            isRunning.value = false
        }
    }

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().scale(timerScale),
                contentAlignment = Alignment.Center
            ) {
                if (isRunning.value) {
                    CircularProgressIndicator(
                        progress = remainingTime.value.toFloat() / selectedDuration.value,
                        modifier = Modifier.size(200.dp),
                        color = Color(0xFF4CAF50),
                        strokeWidth = 8.dp
                    )
                }
                Text(
                    text = formatTime(remainingTime.value),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(Modifier.height(32.dp))
            AnimatedVisibility(
                visible = !isRunning.value,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        OutlinedButton(
                            onClick = { selectedDuration.value = 15 * 60; remainingTime.value = 15 * 60 },
                            modifier = Modifier.padding(8.dp)
                        ) { Text("15 min") }
                        OutlinedButton(
                            onClick = { selectedDuration.value = 30 * 60; remainingTime.value = 30 * 60 },
                            modifier = Modifier.padding(8.dp)
                        ) { Text("30 min") }
                        OutlinedButton(
                            onClick = { selectedDuration.value = 45 * 60; remainingTime.value = 45 * 60 },
                            modifier = Modifier.padding(8.dp)
                        ) { Text("45 min") }
                        OutlinedButton(
                            onClick = { selectedDuration.value = 60 * 60; remainingTime.value = 60 * 60 },
                            modifier = Modifier.padding(8.dp)
                        ) { Text("60 min") }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextField(
                            value = customDuration.value,
                            onValueChange = { customDuration.value = it },
                            label = { Text("Custom (min)") },
                            modifier = Modifier.width(100.dp)
                        )
                        Button(
                            onClick = {
                                val duration = customDuration.value.toIntOrNull() ?: 0
                                if (duration > 0) {
                                    selectedDuration.value = duration * 60
                                    remainingTime.value = duration * 60
                                }
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) { Text("Set") }
                    }
                    Button(
                        onClick = { isRunning.value = true },
                        enabled = selectedDuration.value > 0
                    ) { Text("Start") }
                }
            }
            if (isRunning.value) {
                Button(
                    onClick = {
                        isRunning.value = false
                        remainingTime.value = selectedDuration.value
                    }
                ) { Text("Stop") }
            }
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
            Icon(Icons.Default.Timer, contentDescription = null)
            Text("2h 30m today", modifier = Modifier.padding(start = 8.dp))
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.Star, contentDescription = null)
            Text("5 days", modifier = Modifier.padding(start = 8.dp))
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "%02d:%02d".format(minutes, secs)
}