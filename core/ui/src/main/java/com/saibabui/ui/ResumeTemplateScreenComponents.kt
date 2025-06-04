package com.saibabui.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.saibabui.mylibrary2.R

@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResumeTemplateCard(modifier: Modifier = Modifier) {
    CardView(onClick = { /*TODO*/ }) {
        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_docs),
                contentDescription = "",
                modifier = Modifier,
                contentScale = ContentScale.FillBounds
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Software Engineer Resume",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier
                )
                Text(
                    text = "Clean and minimal design for corporate roles",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier
                )
            }
            Icon(Icons.Filled.HeartBroken, contentDescription = "")
        }
        FlowRow(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 8.dp)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val list1 = listOf("Modern", "Clean", "Minimal", "Design")
            for (i in 0..3) {
                val text = list1[i]
                Box(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(4.dp),
                            color = Color.LightGray.copy(alpha = 0.5f)
                        )
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(4.dp)
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun ResumeTemplateCardPreview(){
    ResumeTemplateCard()
}