package com.juvinal.pay.ui.screens.inApp.transactions.loan

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.model.LoanScheduleDT
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoanScheduleScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val loanScheduleList: List<LoanScheduleDT> = emptyList(),
    val loanId: String = "",
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class LoanScheduleScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(value = LoanScheduleScreenUiState())
    val uiState: StateFlow<LoanScheduleScreenUiState> = _uiState.asStateFlow()

    private val loanId: String? = savedStateHandle[LoanScheduleScreenDestination.loanId]
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
        if(loanId != null) {
            Log.i("LOAN_SCHEDULE", "Getting loan schedule with ID: $loanId")
            getLoanSchedule()
        }
    }

    fun getLoanSchedule() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getLoanSchedule(loanId = loanId!!.toInt())
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            loanScheduleList = response.body()?.data!!
                        )
                    }
                } else {
                    Log.e("TAG", "getLoanSchedule: $response")
                }
            } catch (e: Exception) {
                Log.e("TAG", "getLoanSchedule: $e")
            }
        }
    }

    init {
        loadStartupData()
    }
}