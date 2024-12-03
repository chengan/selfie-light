package com.selfie.light.domain.brightness

import android.content.Context
import android.provider.Settings
import android.provider.Settings.System
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.abs

@Singleton
class BrightnessManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _brightness = MutableStateFlow(0.8f)
    val brightness: Flow<Float> = _brightness

    suspend fun setBrightness(targetPercent: Float) {
        try {
            val currentBrightness = getCurrentBrightness()
            // 如果亮度变化太小，直接设置
            if (abs(currentBrightness - targetPercent) < 0.05f) {
                setSystemBrightness(targetPercent)
                _brightness.emit(targetPercent)
                return
            }

            // 平滑过渡
            val steps = 10
            val stepDuration = 16L // 约60fps
            val brightnessStep = (targetPercent - currentBrightness) / steps

            for (i in 1..steps) {
                val intermediateValue = currentBrightness + (brightnessStep * i)
                setSystemBrightness(intermediateValue)
                _brightness.emit(intermediateValue)
                delay(stepDuration)
            }
        } catch (e: Exception) {
            // 处理异常
        }
    }

    private fun setSystemBrightness(percent: Float) {
        val brightnessValue = (percent * 255).toInt()
        Settings.System.putInt(
            context.contentResolver,
            System.SCREEN_BRIGHTNESS,
            brightnessValue
        )
    }

    fun getCurrentBrightness(): Float {
        return try {
            val brightness = Settings.System.getInt(
                context.contentResolver,
                System.SCREEN_BRIGHTNESS
            )
            brightness / 255f
        } catch (e: Exception) {
            0.8f
        }
    }
} 