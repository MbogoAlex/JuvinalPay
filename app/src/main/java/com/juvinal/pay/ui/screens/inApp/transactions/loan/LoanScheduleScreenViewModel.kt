package com.juvinal.pay.ui.screens.inApp.transactions.loan

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.LoanScheduleDT
import com.juvinal.pay.model.dbModel.UserDetails
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoanScheduleScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val loanScheduleList: List<LoanScheduleDT> = emptyList(),
    val unpaidSchedule: List<LoanScheduleDT> = emptyList(),
    val paidSchedule: List<LoanScheduleDT> = emptyList(),
    val loanId: String = "",
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class LoanScheduleScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = LoanScheduleScreenUiState())
    val uiState: StateFlow<LoanScheduleScreenUiState> = _uiState.asStateFlow()

    private val loanId: String? = savedStateHandle[LoanScheduleScreenDestination.loanId]
    fun loadStartupData() {
        viewModelScope.launch {
            val appLaunchStatus = dbRepository.getAppLaunchState(1)
            _uiState.update {
                it.copy(
                    userDetails = dbRepository.getUserDetails(appLaunchStatus.user_id!!).first(),
                    loanId = loanId!!
                )
            }
            getLoanSchedule()
        }
    }

    fun getLoanSchedule() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getLoanSchedule(loanId = loanId!!.toInt())
                if(response.isSuccessful) {
                    val unpaidSchedule = mutableListOf<LoanScheduleDT>()
                    val paidSchedule = mutableListOf<LoanScheduleDT>()
                    for(loanSchedule in response.body()?.data!!) {
                        if(loanSchedule.schedule_total_balance.toDouble() != 0.0) {
                            unpaidSchedule.add(loanSchedule)
                        } else {
                            paidSchedule.add(loanSchedule)
                        }
                    }
                    Log.i("UNPAID_SCHEDULES", unpaidSchedule[0].toString())
                    _uiState.update {
                        it.copy(
                            loanScheduleList = response.body()?.data!!,
                            unpaidSchedule = unpaidSchedule,
                            paidSchedule = paidSchedule
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