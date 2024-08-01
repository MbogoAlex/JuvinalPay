package com.juvinal.pay.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.datastore.AppLaunchState
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.dbModel.AppLaunchStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WelcomeScreenUiState(
    val appLaunchStatus: AppLaunchStatus = AppLaunchStatus()
)
class WelcomeScreenViewModel(
//    private val dsRepository: DSRepository,
    private val dbRepository: DBRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(value = WelcomeScreenUiState())
    val uiState: StateFlow<WelcomeScreenUiState> = _uiState.asStateFlow()

    fun loadLaunchStatus() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    appLaunchStatus = dbRepository.getAppLaunchState(1)
                )
            }
            saveLaunchState()
        }
    }

    fun saveLaunchState() {
        viewModelScope.launch {
            dbRepository.updateAppLaunchState(
                uiState.value.appLaunchStatus.copy(
                    launched = 1
                )
            )
        }
    }

    init {
        loadLaunchStatus()
    }
}