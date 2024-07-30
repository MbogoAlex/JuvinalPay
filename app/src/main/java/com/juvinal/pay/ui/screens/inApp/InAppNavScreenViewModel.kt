package com.juvinal.pay.ui.screens.inApp

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class InAppNavScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(InAppNavScreenUiState())
    val uiState: StateFlow<InAppNavScreenUiState> = _uiState.asStateFlow()

    private val childScreen: String? = savedStateHandle[InAppNavScreenDestination.childScreen]

    fun loadStartupData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    userDetails = dsRepository.userDSDetails.first().toUserDetails(),
                    childScreen = childScreen
                )
            }
            getDashboardDetails()
        }
    }

    fun getDashboardDetails() {
        Log.i("DASHBOARD_DETAILS_WITH_USER_ID", uiState.value.userDetails.id.toString())
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.INITIAL
            )
        }
        viewModelScope.launch {
            try {
                val response = apiRepository.getDashboardDetails(uiState.value.userDetails.id!!)
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