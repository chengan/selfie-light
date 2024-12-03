package com.selfie.light.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.selfie.light.utils.PermissionManager
import androidx.compose.animation.*
import androidx.compose.animation.core.*

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        viewModel.checkPermission(
            PermissionManager.hasWriteSettingsPermission(context)
        )
    }

    if (!uiState.hasPermission) {
        PermissionDialog(
            onConfirm = {
                PermissionManager.openWriteSettingsPermission(context)
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .align(if (isLandscape) Alignment.BottomCenter else Alignment.CenterEnd)
        ) {
            BrightnessIndicator(
                brightness = uiState.brightness,
                isLandscape = isLandscape
            )
            BrightnessSlider(
                brightness = uiState.brightness,
                onBrightnessChange = { viewModel.setBrightness(it) },
                isLandscape = isLandscape,
                modifier = Modifier
                    .padding(16.dp)
                    .let {
                        if (isLandscape) {
                            it.width(200.dp).height(48.dp)
                        } else {
                            it.width(48.dp).height(200.dp)
                        }
                    }
            )
        }
        BottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun BrightnessSlider(
    brightness: Float,
    onBrightnessChange: (Float) -> Unit,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    var isSliding by remember { mutableStateOf(false) }
    val sliderScale by animateFloatAsState(
        targetValue = if (isSliding) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        modifier = modifier
            .scale(sliderScale)
            .animateContentSize()
    ) {
        Slider(
            value = brightness,
            onValueChange = { 
                onBrightnessChange(it)
                isSliding = true
            },
            onValueChangeFinished = {
                isSliding = false
            },
            valueRange = 0.2f..1f,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ),
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = if (isLandscape) 0f else 270f
                }
        )
    }
}

@Composable
private fun BrightnessIndicator(
    brightness: Float,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedBrightness by animateFloatAsState(
        targetValue = brightness,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier
            .padding(8.dp)
            .let {
                if (isLandscape) {
                    it.offset(y = (-32).dp)
                } else {
                    it.offset(x = (-32).dp)
                }
            }
    ) {
        Text(
            text = "${(animatedBrightness * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun PermissionDialog(
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("需要权限") },
        text = { Text("需要屏幕亮度控制权限来调节亮度") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("去设置")
            }
        }
    )
}

enum class SliderOrientation {
    Vertical,
    Horizontal
}

@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    orientation: SliderOrientation = SliderOrientation.Horizontal
) {
    when (orientation) {
        SliderOrientation.Vertical -> {
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = valueRange,
                modifier = modifier
            )
        }
        SliderOrientation.Horizontal -> {
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = valueRange,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White.copy(alpha = 0.9f)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 添加功能按钮
    }
} 