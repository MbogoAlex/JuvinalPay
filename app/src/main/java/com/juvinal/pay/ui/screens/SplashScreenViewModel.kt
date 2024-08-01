package com.juvinal.pay.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.dbModel.AppLaunchStatus
import com.juvinal.pay.model.dbModel.UserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SplashScreenUiState(
    val navigated: Boolean = false,
    val appLaunched: Boolean = false,
    val userDetails: UserDetails = UserDetails(),
    val appLaunchStatus: AppLaunchStatus = AppLaunchStatus()
)
class SplashScreenViewModel(
    private val dsRepository: DSRepository,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SplashScreenUiState())
    val uiState: StateFlow<SplashScreenUiState> = _uiState.asStateFlow()
    fun loadUserDetails(userId: Int) {
        viewModelScope.launch {
            val user = dbRepository.getUser(userId).first()
            val member = dbRepository.getMember(userId).first()
            val userDetails = UserDetails(
                user = user,
                member = member
            )
            _uiState.update {
                it.copy(
                    userDetails = userDetails
                )
            }
            Log.d("USER_FETCHED:", userDetails.toString())
        }

    }

    fun loadLaunchDetails() {
        viewModelScope.launch {
            val appLaunchStatus = dbRepository.getAppLaunchState(1)
            _uiState.update {
                it.copy(
                    appLaunchStatus = appLaunchStatus,
                    appLaunched = appLaunchStatus.launched == 1
                )
            }
            if(uiState.value.appLaunchStatus.user_id != null) {
                loadUserDetails(uiState.value.appLaunchStatus.user_id!!)
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
        loadLaunchDetails()
    }
}