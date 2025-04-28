package com.saibabui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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


@Composable
fun TemplateCategoryCard(modifier: Modifier = Modifier) {
    Column {

    }
}