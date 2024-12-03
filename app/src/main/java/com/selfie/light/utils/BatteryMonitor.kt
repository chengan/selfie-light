package com.selfie.light.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import javax.inject.Inject

class BatteryMonitor @Inject constructor(
    private val context: Context
) {
    fun getBatteryTemperature(): Float {
        val intent = context.registerReceiver(null, 
            IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        return intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)?.div(10f) ?: 0f
    }
} 