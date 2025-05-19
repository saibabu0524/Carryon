package com.saibabui.ui

import androidx.cardview.widget.CardView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.saibabui.mylibrary2.R

@Composable
fun NewResumeCard(
    modifier: Modifier = Modifier,
    cardTitle: String,
    cardDescription: String? = null,
    cardIcon: Int,
    onClick: () -> Unit
) {
    CardView(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = cardIcon),
                contentDescription = "Card Icon",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = cardTitle,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            cardDescription?.let {
                Text(
                    text = cardDescription,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}


@Composable
fun RecentActivityCard(
    modifier: Modifier = Modifier,
    resumeTitle: String,
    lastUpdated: String,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Handle click on the entire card
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = resumeTitle,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Box(
                    modifier = Modifier.background(
                        shape = RoundedCornerShape(4.dp),
                        color = Color.LightGray.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
            Text(
                text = "Last updated on $lastUpdated",
                style = MaterialTheme.typography.labelLarge,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecentActivityCardPreview(modifier: Modifier = Modifier) {
    RecentActivityCard(
        resumeTitle = "Resume Title",
        lastUpdated = "April 25,2025",
        tag = "Modern",
        onClick = {})
}


@Composable
fun CardView(modifier: Modifier = Modifier, onClick: () -> Unit, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray,
        ),
        onClick = onClick,
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White,
            contentColor = Color.Black
        ), shape = RoundedCornerShape(8.dp)
    ) {
        content()
    }

}


@Composable
fun HomePremiumSubscriptionCard(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Black, contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Premium Features", style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Icon(Icons.Filled.WorkspacePremium, contentDescription = "Premium icon")
            }
            Text(
                text = "Get access to 50+ premium templates and AI-powered suggestions",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PrimaryButton(
                buttonText = "Upgrade Now",
                onClick = { },
                modifier = Modifier,
                buttonColors = ButtonDefaults.buttonColors().copy(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomePremiumSubscriptionCardPreview() {
    HomePremiumSubscriptionCard(modifier = Modifier, onClick = {})
}

@Composable
fun HomeScreenRecentCard(
    modifier: Modifier = Modifier,
    resumeTitle: String,
    template: String,
    lastUpdated: String,
    completionStatus: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier.background(
                            shape = RoundedCornerShape(4.dp),
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )
                    ) {
                        Icon(
                            Icons.Filled.Code,
                            contentDescription = "Icon",
                            modifier = Modifier
                                .size(32.dp)
                                .padding(8.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "Software Engineer Resume",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier
                        )
                        Text(text = "Modern Template")
                    }
                }
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(8.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Last updated on $lastUpdated",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.DarkGray, // Dark gray for secondary text
                    modifier = Modifier.alpha(0.5f)
                )
                Box(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(4.dp),
                            color = Color.White // White background for status
                        )
                        .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp)) // Black border
                ) {
                    Text(
                        text = completionStatus,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black, // Black text
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProTipCards(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.LightGray.copy(), shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                Icons.Filled.Lightbulb,
                contentDescription = "",
                modifier = Modifier.size(20.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(text = "Pro tip", style = MaterialTheme.typography.titleMedium)
        }
        Text(
            text = "Use action verbs and quality achievements to make your resume stand out",
            modifier = Modifier
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewResumeCardPreview() {
    NewResumeCard(cardTitle = "New Resume", cardIcon = R.drawable.google_docs, onClick = {})
}