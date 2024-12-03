package com.selfie.light.ui.common

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object NavigateToSettings : UiEvent()
    object NavigateToMain : UiEvent()
} 