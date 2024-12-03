package com.selfie.light.ui.guide

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selfie.light.R
import com.selfie.light.ui.common.UiEvent
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Composable
fun GuideScreen(
    onNavigate: (UiEvent) -> Unit,
    viewModel: GuideViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val pages = listOf(
        GuidePageData(
            title = "欢迎使用自拍补光",
            description = "让您的自拍更加完美",
            imageRes = R.drawable.guide_1
        ),
        GuidePageData(
            title = "简单操作",
            description = "滑动调节亮度，轻松获得最佳效果",
            imageRes = R.drawable.guide_2
        ),
        GuidePageData(
            title = "开始使用",
            description = "点击开始按钮，体验完美自拍",
            imageRes = R.drawable.guide_3
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            count = pages.size,
            state = pagerState
        ) { page ->
            GuidePage(
                data = pages[page],
                isLastPage = page == pages.size - 1,
                onStartClick = {
                    viewModel.completeGuide()
                    onNavigate(UiEvent.NavigateToMain)
                }
            )
        }

        // 页面指示器
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                val selected = index == pagerState.currentPage
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (selected) 10.dp else 8.dp)
                        .background(
                            color = if (selected) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.small
                        )
                )
            }
        }
    }
}

@Composable
private fun GuidePage(
    data: GuidePageData,
    isLastPage: Boolean,
    onStartClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = data.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 32.dp)
        )

        Text(
            text = data.title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = data.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (isLastPage) {
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .width(200.dp)
            ) {
                Text("开始使用")
            }
        }
    }
}

data class GuidePageData(
    val title: String,
    val description: String,
    val imageRes: Int
) 