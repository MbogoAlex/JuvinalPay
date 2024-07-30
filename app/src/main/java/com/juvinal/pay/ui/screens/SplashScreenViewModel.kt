package com.juvinal.pay.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SplashScreenUiState(
    val navigated: Boolean = false,
    val appLaunched: Boolean = false,
    val userDetails: UserDetails = UserDetails()
)
class SplashScreenViewModel(
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SplashScreenUiState())
    val uiState: StateFlow<SplashScreenUiState> = _uiState.asStateFlow()
    fun loadUserDetails() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    userDetails = dsRepository.userDSDetails.first().toUserDetails()
                )
            }
        }
    }

    fun loadLaunchDetails() {
        viewModelScope.launch {
            dsRepository.launchState.collect(){launchState->
                _uiState.update {
                    it.copy(
                        appLaunched = launchState.launched
                    )
                }
            }
        }
    }

    fun changeNavigationStatus() {
        _uiState.update {
            it.copy(
                navigated = true
            )
        }
    }

    init {
        loadUserDetails()
        loadLaunchDetails()
    }
}