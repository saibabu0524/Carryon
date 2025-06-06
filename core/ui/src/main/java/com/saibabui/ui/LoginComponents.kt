package com.saibabui.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saibabui.mylibrary2.R
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HeadingText(headingText: String) {
    Text(
        text = headingText,
        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.onBackground
    )
}


@Composable
fun Description(description: String) {
    Text(
        text = description,
        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
}


val CustomLabelForTextField: @Composable (labelText: String, textColor: Color) -> Unit =
    { text, textColor ->
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }

val CustomButtonText: @Composable (String) -> Unit = {
    Text(
        text = it,
        fontSize = 16.sp,
        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun CustomeTextField(
    supportingText: String,
    internalState: MutableStateFlow<CustomTextFieldState>,
    keyboardType: KeyboardType = KeyboardType.Text,
    hint: String = "Enter email",
    modifier: Modifier = Modifier,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
    onValueChange: (String) -> Unit = {}
) {
    val state = internalState.collectAsState().value
    Column {
        CustomLabelForTextField(
            supportingText,
            if (state.error.isEmpty()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        )
        OutlinedTextField(
            value = state.value,
            onValueChange = {
                onValueChange(it)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedIndicatorColor = LightGray, // Light gray outline when unfocused
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                focusedIndicatorColor = LightGray, // Light gray outline when focused
            ),
            modifier = Modifier.fillMaxWidth(),
            isError = state.error.isNotEmpty(),
            supportingText = {
                if (state.error.isNotEmpty()) {
                    Text(
                        text = state.error,
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            maxLines = 1,
            placeholder = {
                Text(
                    text = hint,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f) // Lighter color for hint
                )
            },
            shape = RoundedCornerShape(8.dp),
            visualTransformation = visualTransformation
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CustomTextFieldPrev() {
    CustomeTextField(
        supportingText = "Email",
        internalState = MutableStateFlow(CustomTextFieldState())
    )
}

@Composable
fun PrimaryButton(
    buttonText: String,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth()
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

@Preview
@Composable
fun PrimaryButtonPreview(modifier: Modifier = Modifier) {
    PrimaryButton(buttonText = "Login", onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth())
}

@Composable
fun SecondaryButton(buttonText: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth(),
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
fun TopBar(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Icon(
            painter = painterResource(id = R.drawable.google_docs),
            contentDescription = "",
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .clickable {
                    onClick()
                }

        )
    }
}