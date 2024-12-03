package com.selfie.light.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "brightness_settings")

@Singleton
class BrightnessPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val LAST_BRIGHTNESS = floatPreferencesKey("last_brightness")
    }

    suspend fun saveLastBrightness(brightness: Float) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_BRIGHTNESS] = brightness
        }
    }

    fun getLastBrightness(): Flow<Float> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.LAST_BRIGHTNESS] ?: 0.8f
    }
} 