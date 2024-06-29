package com.juvinal.pay.ui.screens.inApp.transactions.transactionsHistory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.model.LoanHistoryDt
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoanHistoryScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val loanHistory: List<LoanHistoryDt> = emptyList(),
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class LoanHistoryScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = LoanHistoryScreenUiState())
    val uiState: StateFlow<LoanHistoryScreenUiState> = _uiState.asStateFlow()

    fun loadStartupData() {
        viewModelScope.launch {
            dsRepository.userDSDetails.collect() {dsUserDetails->
                _uiState.update {
                    it.copy(
                        userDetails = dsUserDetails.toUserDetails()
                    )
                }
            }
        }
        if(uiState.value.userDetails.id != null) {
            getLoanHistory()
        }
    }

    fun getLoanHistory() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getLoansHistory(uiState.value.userDetails.mem_no!!)
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            loanHistory = response.body()?.data!!,
                            loadingStatus = LoadingStatus.SUCCESS
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                    Log.e("LOAN_HISTORY_FETCH_ERROR_RESPONSE", response.toString())
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("LOAN_HISTORY_FETCH_ERROR_EXCEPTION", e.toString())

            }
        }
    }

    init {
        loadStartupData()
    }
}