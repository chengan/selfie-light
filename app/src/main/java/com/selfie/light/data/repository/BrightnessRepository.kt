package com.selfie.light.data.repository

import com.selfie.light.data.local.BrightnessPreferences
import com.selfie.light.domain.brightness.BrightnessManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrightnessRepository @Inject constructor(
    private val brightnessManager: BrightnessManager,
    private val brightnessPreferences: BrightnessPreferences
) {
    val currentBrightness: Flow<Float> = brightnessManager.brightness

    suspend fun setBrightness(brightness: Float) {
        brightnessManager.setBrightness(brightness)
        brightnessPreferences.saveLastBrightness(brightness)
    }

    fun getLastBrightness(): Flow<Float> = brightnessPreferences.getLastBrightness()

    fun getCurrentSystemBrightness(): Float = brightnessManager.getCurrentBrightness()
} 