package com.selfie.light

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.core.view.WindowCompat
import com.selfie.light.ui.common.rememberSystemUiController
import com.selfie.light.ui.navigation.Navigation
import com.selfie.light.ui.theme.SelfieLightTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 允许横竖屏切换
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        
        setContent {
            val systemUiController = rememberSystemUiController(window)
            
            // 处理系统UI
            DisposableEffect(systemUiController) {
                systemUiController.hideSystemBars()
                onDispose {
                    systemUiController.showSystemBars()
                }
            }
            
            SelfieLightTheme {
                Navigation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkSystemBrightness()
    }
} 