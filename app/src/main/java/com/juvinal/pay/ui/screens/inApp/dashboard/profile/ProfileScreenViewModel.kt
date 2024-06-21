package com.juvinal.pay.ui.screens.inApp.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileScreenUiState(
    val userDetails: UserDetails = UserDetails()
)
class ProfileScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> = _uiState.asStateFlow()
    fun loadStartupData() {
        viewModelScope.launch {
            dsRepository.userDSDetails.collect(){dsUserDetails->
                _uiState.update {
                    it.copy(
                        userDetails = dsUserDetails.toUserDetails()
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dsRepository.clear()
        }
    }

    init {
        loadStartupData()
    }
}