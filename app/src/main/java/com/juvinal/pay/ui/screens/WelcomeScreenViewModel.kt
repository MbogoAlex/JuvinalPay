package com.juvinal.pay.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.datastore.AppLaunchState
import com.juvinal.pay.datastore.DSRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WelcomeScreenUiState(
    val appLaunchState: AppLaunchState = AppLaunchState(false)
)
class WelcomeScreenViewModel(
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = WelcomeScreenUiState())
    val uiState: StateFlow<WelcomeScreenUiState> = _uiState.asStateFlow()

    fun saveLaunchState() {
        viewModelScope.launch {
            dsRepository.saveLaunchState(AppLaunchState(true))
        }
    }
}