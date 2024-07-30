package com.juvinal.pay.ui.screens.inApp.transactions.transactionsHistory

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.model.TransactionHistoryData
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TransactionHistoryItem(
    val tran_status: String = "",
    val tran_amt: String = "0.0",
    val payment_mode: String = "",
    val share_month: String = "",
    val desc: String = ""
)

data class DepositHistoryScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val transactions: List<TransactionHistoryData> = emptyList(),
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL,
)
class DepositHistoryScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(value = DepositHistoryScreenUiState())
    val uiState: StateFlow<DepositHistoryScreenUiState> = _uiState.asStateFlow()
    fun loadStartupData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    userDetails = dsRepository.userDSDetails.first().toUserDetails()
                )
            }
            getTransactionsHistory()
        }
    }

    fun getTransactionsHistory() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        viewModelScope.launch {
            try {
                val response = apiRepository.getTransactionHistory(uiState.value.userDetails.id!!)
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            transactions = response.body()?.data!!,
                            loadingStatus = LoadingStatus.SUCCESS
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                    Log.e("TRANSACTIONS_HISTORY_ERROR_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("TRANSACTIONS_HISTORY_EXCEPTION", e.toString())
            }
        }
    }

    init {
        loadStartupData()
    }
}