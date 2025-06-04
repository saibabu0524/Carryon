package com.saibabui.ui.shimmer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerTemplateCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Preview image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(shimmerBrush(targetValue = 300f))
            )

            // Template name placeholder
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.7f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush(targetValue = 300f))
            )

            // Category placeholder
            Box(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
                    .width(80.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(shimmerBrush(targetValue = 300f))
            )
        }
    }
}