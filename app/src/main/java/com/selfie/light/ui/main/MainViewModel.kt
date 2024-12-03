package com.selfie.light.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selfie.light.data.repository.BrightnessRepository
import com.selfie.light.utils.PermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BrightnessRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getLastBrightness().collect { brightness ->
                _uiState.value = _uiState.value.copy(
                    brightness = brightness
                )
            }
        }
    }

    private fun handleError(error: Throwable) {
        _uiState.value = _uiState.value.copy(
            isError = true,
            errorMessage = error.localizedMessage ?: "未知错误"
        )
    }

    fun setBrightness(brightness: Float) {
        viewModelScope.launch {
            try {
                repository.setBrightness(brightness)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun checkPermission(hasPermission: Boolean) {
        _uiState.value = _uiState.value.copy(
            hasPermission = hasPermission
        )
    }
}

data class MainUiState(
    val brightness: Float = 0.8f,
    val hasPermission: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) 