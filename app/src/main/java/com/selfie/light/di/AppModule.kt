package com.selfie.light.di

import android.content.Context
import com.selfie.light.data.local.BrightnessPreferences
import com.selfie.light.data.repository.BrightnessRepository
import com.selfie.light.domain.brightness.BrightnessManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideBrightnessPreferences(
        @ApplicationContext context: Context
    ): BrightnessPreferences = BrightnessPreferences(context)

    @Provides
    @Singleton
    fun provideBrightnessManager(
        @ApplicationContext context: Context
    ): BrightnessManager = BrightnessManager(context)

    @Provides
    @Singleton
    fun provideBrightnessRepository(
        brightnessManager: BrightnessManager,
        brightnessPreferences: BrightnessPreferences
    ): BrightnessRepository = BrightnessRepository(brightnessManager, brightnessPreferences)
} 