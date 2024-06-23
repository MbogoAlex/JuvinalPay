package com.juvinal.pay.ui.screens.inApp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InAppNavScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val childScreen: String? = ""
)
class InAppNavScreenViewModel(
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(InAppNavScreenUiState())
    val uiState: StateFlow<InAppNavScreenUiState> = _uiState.asStateFlow()

    private val childScreen: String? = savedStateHandle[InAppNavScreenDestination.childScreen]

    fun loadStartupData() {
        viewModelScope.launch {
            dsRepository.userDSDetails.collect(){dsUserDetails->
                _uiState.update {
                    it.copy(
                        userDetails = dsUserDetails.toUserDetails(),
                        childScreen = childScreen
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

    fun resetChildScreen() {
        _uiState.update {
            it.copy(
                childScreen = ""
            )
        }
    }

    init {
        loadStartupData()
    }
}