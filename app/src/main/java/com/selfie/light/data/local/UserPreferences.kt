package com.selfie.light.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.userDataStore

    private object PreferencesKeys {
        val FIRST_TIME_USER = booleanPreferencesKey("first_time_user")
    }

    val isFirstTimeUser: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.FIRST_TIME_USER] ?: true
    }

    suspend fun setFirstTimeUserComplete() {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_TIME_USER] = false
        }
    }
} 