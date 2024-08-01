package com.juvinal.pay.ui.screens.inApp.transactions.loan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.LoanHistoryDt
import com.juvinal.pay.model.dbModel.UserDetails
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UnpaidLoansScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val unpaidLoans: List<LoanHistoryDt> = emptyList(),
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class UnpaidLoansScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = UnpaidLoansScreenUiState())
    val uiState: StateFlow<UnpaidLoansScreenUiState> = _uiState.asStateFlow()

    fun loadStartupData() {
        viewModelScope.launch {
            val appLaunchStatus = dbRepository.getAppLaunchState(1)
            _uiState.update {
                it.copy(
                    userDetails = dbRepository.getUserDetails(appLaunchStatus.user_id!!).first()
                )
            }
            getLoanHistory()
        }
    }

    fun getLoanHistory() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getLoansHistory(uiState.value.userDetails.member?.mem_no!!)
                if(response.isSuccessful) {
                    val unpaidLoans = mutableListOf<LoanHistoryDt>()
                    for(loan in response.body()?.data!!) {
                        if(!loan.loan_disbursed_date.isNullOrEmpty() && loan.loan_outstanding_bal != loan.loan_approved_amount) {
                            unpaidLoans.add(loan)
                        }
                    }
                    _uiState.update {
                        it.copy(
                            unpaidLoans = unpaidLoans,
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