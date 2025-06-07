package com.saibabui.auth.presentation.ui.dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.saibabui.auth.R
import com.saibabui.auth.presentation.ui.signup.navigateToSignUp
import com.saibabui.ui.HeadingText
import com.saibabui.ui.PrimaryButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashBoardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column(
        modifier = Modifier
            .then(modifier)
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            val isCurrentPage = pagerState.currentPage == page
            val scale by animateFloatAsState(
                targetValue = if (isCurrentPage) 1f else 0.8f,
                label = "scale"
            )
            val alpha by animateFloatAsState(
                targetValue = if (isCurrentPage) 1f else 0.5f,
                label = "alpha"
            )

            PagerContent(
                page = page,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(scale)
                    .alpha(alpha)
                    .padding(16.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(pagerState.pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (isSelected) 12.dp else 8.dp)
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                )
            }
        }

        PrimaryButton(
            buttonText = stringResource(R.string.sign_up_with_email),
        ) {
            navController.navigateToSignUp()
        }
    }
}

@Composable
fun PagerContent(
    page: Int,
    modifier: Modifier = Modifier
) {
    val (title, description, imageRes) = when (page) {
        0 -> Triple(
            stringResource(R.string.create_your_resume),
            stringResource(R.string.create_resume_description),
            R.drawable.dashboard_1
        )
        1 -> Triple(
            stringResource(R.string.carry_your_resume),
            stringResource(R.string.carry_resume_description),
            R.drawable.dashboard_2 // Placeholder: Add your image
        )
        else -> Triple(
            stringResource(R.string.latex_support),
            stringResource(R.string.latex_support_description),
            R.drawable.dashboard_1 // Placeholder: Add your image
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
        )
        HeadingText(
            headingText = title
        )
        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DashBoardPreview() {
    MaterialTheme {
        Surface {
            DashBoardScreen(navController = rememberNavController())
        }
    }
}