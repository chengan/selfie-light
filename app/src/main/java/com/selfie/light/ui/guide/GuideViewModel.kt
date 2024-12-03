package com.selfie.light.ui.guide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selfie.light.data.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuideViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    fun completeGuide() {
        viewModelScope.launch {
            userPreferences.setFirstTimeUserComplete()
        }
    }
} 