package com.selfie.light.ui.common

import android.app.Activity
import android.view.View
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun rememberSystemUiController(
    window: android.view.Window,
    view: View = LocalView.current
): SystemUiController {
    return remember(window, view) {
        SystemUiController(window, view)
    }
}

class SystemUiController(
    private val window: android.view.Window,
    private val view: View
) {
    private val windowInsetsController = WindowInsetsControllerCompat(window, view)

    fun hideSystemBars() {
        // 保持屏幕常亮
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // 隐藏系统栏
        windowInsetsController.apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        // 设置全屏
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun showSystemBars() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }
} 