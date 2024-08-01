package com.juvinal.pay.ui.screens.inApp

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.dbModel.AppLaunchStatus
import com.juvinal.pay.model.dbModel.UserDetails
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InAppNavScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val childScreen: String? = "",
    val accountSavings: Double = 0.0,
    val loanBalance: Double = 0.0,
    val guaranteedAmounts: Double = 0.0,
    val netSavings: Double = 0.0,
    val accountShareCapital: Double = 0.0,
    val loanAmountQualified: Double = 0.0,
    val appLaunchStatus: AppLaunchStatus = AppLaunchStatus(),
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class InAppNavScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val dbRepository: DBRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(InAppNavScreenUiState())
    val uiState: StateFlow<InAppNavScreenUiState> = _uiState.asStateFlow()

    private val childScreen: String? = savedStateHandle[InAppNavScreenDestination.childScreen]

    fun loadStartupData() {
        viewModelScope.launch {
            val appLaunchStatus = dbRepository.getAppLaunchState(1)
            _uiState.update {
                it.copy(
                    userDetails = dbRepository.getUserDetails(appLaunchStatus.user_id!!).first(),
                    appLaunchStatus = appLaunchStatus,
                    childScreen = childScreen
                )
            }
            Log.d("USER_DATA", uiState.value.userDetails.toString())
            if(uiState.value.userDetails.user!!.user_id != 0) {
                getDashboardDetails()
            }
        }
    }

    fun getDashboardDetails() {
        Log.i("DASHBOARD_DETAILS_WITH_USER_ID", uiState.value.userDetails.user!!.user_id.toString())
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.INITIAL
            )
        }
        viewModelScope.launch {
            try {
                val response = apiRepository.getDashboardDetails(uiState.value.userDetails.user!!.user_id)
                if(response.isSuccessful) {
                    Log.i("DASHBOARD_DETAILS:", "${response.body()?.data?.accountSavings!!.toDouble()}")
                    _uiState.update {
                        it.copy(
                            accountSavings = response.body()?.data?.accountSavings!!.toDouble(),
                            loanBalance = response.body()?.data?.loanBalance!!,
                            guaranteedAmounts = response.body()?.data?.guaranteedAmounts!!,
                            netSavings = response.body()?.data?.netSavings!!,
                            accountShareCapital = response.body()?.data?.accountShareCapital!!.toDouble(),
                            loanAmountQualified = response.body()?.data?.loanAmountQualified!!.toDouble(),
                            loadingStatus = LoadingStatus.SUCCESS
                        )
                    }
                    Log.i("ACCOUNTS_SAVING", uiState.value.accountSavings.toString())
                } else {
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                    Log.e("DASHBOARD_ERROR_RESPONSE", response.toString())
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("DASHBOARD_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dbRepository.deleteUserDetails(
                user = uiState.value.userDetails.user!!,
                member = uiState.value.userDetails.member!!
            )
            dbRepository.updateAppLaunchState(
                uiState.value.appLaunchStatus.copy(
                    user_id = null
                )
            )
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