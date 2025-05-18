package com.saibabui.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import com.saibabui.mylibrary2.R

@Composable
fun TemplateSelectionButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    isSelected: Boolean,
    oncClick: () -> Unit
) {
    Button(
        onClick = oncClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Black else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        ), shape = CircleShape,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = buttonText)
    }
}


@Preview
@Composable
private fun TemplateSelectionButtonPreview() {
    TemplateSelectionButton(buttonText = "Modern", isSelected = true) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TemplateTypeCard(modifier: Modifier = Modifier) {
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

        Text(
            text = "Software Engineer Resume",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp).background(
                shape = RoundedCornerShape(4.dp),
                color = Color.LightGray.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = "85% Complete",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(4.dp)
            )
        }

    }
}


@Composable
fun TemplateCategoryCard(modifier: Modifier = Modifier) {
    Column {

    }
}