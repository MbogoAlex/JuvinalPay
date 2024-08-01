package com.juvinal.pay.ui.screens.inApp.dashboard.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.dbModel.UserDetails
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChangePasswordScreenUiState(
    val userDetails: UserDetails = UserDetails()
)
class ChangePasswordScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ChangePasswordScreenUiState())
    val uiState: StateFlow<ChangePasswordScreenUiState> = _uiState.asStateFlow()
    fun loadStartupData() {
        viewModelScope.launch {
            val appLaunchStatus = dbRepository.getAppLaunchState(1)
            _uiState.update {
                it.copy(
                    userDetails = dbRepository.getUserDetails(appLaunchStatus.user_id!!).first()
                )
            }
        }
    }



    init {
        loadStartupData()
    }
}