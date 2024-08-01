package com.juvinal.pay.ui.screens.inApp.transactions.loan

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.LoanRepaymentPayload
import com.juvinal.pay.model.dbModel.UserDetails
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoanRepaymentScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val paymentReferenceId: String? = "",
    val amount: String = "",
    val amountPaid: Double = 0.0,
    val phoneNumber: String = "",
    val loanId: String = "",
    val memNo: String = "",
    val schedulePayDate: String = "",
    val scheduleTotal: String = "",
    val scheduleTotalPaid: String = "",
    val scheduleTotalBalance: String = "",
    val statusCheckMessage: String = "",
    val showSuccessDialog: Boolean = false,
    val paymentButtonEnabled: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class LoanRepaymentScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(LoanRepaymentScreenUiState())
    val uiState: StateFlow<LoanRepaymentScreenUiState> = _uiState.asStateFlow()

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected


    var conManager: ConnectivityManager? = null
    var netCallback: ConnectivityManager.NetworkCallback? = null
    fun checkConnectivity(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        conManager = connectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isConnected.postValue(true)
            }

            override fun onLost(network: Network) {
                _isConnected.postValue(false)
            }

        }

        netCallback = networkCallback

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onCleared() {
        super.onCleared()
        conManager!!.unregisterNetworkCallback(netCallback!!)
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update {
            it.copy(
                phoneNumber = phoneNumber
            )
        }
    }

    fun updateAmount(amount: String) {
        _uiState.update {
            it.copy(
                amount = amount,
                amountPaid = if(amount.isNotEmpty()) amount.replace(",", "").toDouble() else 0.0
            )
        }
    }

    fun loadStartupDetails() {
        viewModelScope.launch {
            val appLaunchStatus = dbRepository.getAppLaunchState(1)
            val user = dbRepository.getUserDetails(appLaunchStatus.user_id!!).first()
            _uiState.update {
                it.copy(
                    userDetails = user,
                    phoneNumber = user.user.phone_no,
                    loanId = savedStateHandle[LoanRepaymentScreenDestination.loanId] ?: "",
                    memNo = savedStateHandle[LoanRepaymentScreenDestination.memNo] ?: "",
                    schedulePayDate = savedStateHandle[LoanRepaymentScreenDestination.schedulePayDate] ?: "",
                    scheduleTotal = savedStateHandle[LoanRepaymentScreenDestination.scheduleTotal] ?: "",
                    scheduleTotalPaid = savedStateHandle[LoanRepaymentScreenDestination.scheduleTotalPaid] ?: "",
                    scheduleTotalBalance = savedStateHandle[LoanRepaymentScreenDestination.scheduleTotalBalance] ?: "",
                )
            }
        }
    }

    fun initiatePayment() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        val loanRepaymentPayload = LoanRepaymentPayload(
            loan_id = uiState.value.loanId.toInt(),
            mem_no = uiState.value.memNo,
            uid = uiState.value.userDetails.user.uid,
            msisdn = uiState.value.phoneNumber,
            payment_purpose = "LOAN_REPAYMENT",
            loan_repayment_amount = uiState.value.amount.toDouble(),
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.payLoan(loanRepaymentPayload)
                Log.i("RESPONSE:", response.body().toString())
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            paymentReferenceId = response.body()?.paymentReference,
                            amount = ""
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                    Log.e("DEPOSIT_ERROR_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("DEPOSIT_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun checkPaymentStatus() {
        Log.i("REFERENCE_ID:", uiState.value.paymentReferenceId!!)
        viewModelScope.launch {
            try {
                val response = apiRepository.checkPaymentStatus(uiState.value.paymentReferenceId!!)
                Log.i("RESPONSE:", response.toString())
                if(response.isSuccessful) {
                    if(response.body()?.status?.lowercase() == "successful") {

                        _uiState.update {
                            it.copy(
                                loadingStatus = LoadingStatus.SUCCESS
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                loadingStatus = LoadingStatus.FAIL,
                                statusCheckMessage = "Payment not successful"
                            )
                        }
                        Log.e("PAYMENT_STATUS_CHECK_NOT_SUCCESS", response.toString())
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                    Log.e("PAYMENT_STATUS_CHECK_ERROR_RESPONSE", response.toString())
                }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("PAYMENT_STATUS_CHECK_EXCEPTION", e.toString())
            }
        }
    }

    fun resetLoadingStatus() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.INITIAL
            )
        }
    }

    fun toggleDepositSuccessDialog(status: Boolean) {
        _uiState.update {
            it.copy(
                showSuccessDialog = status
            )
        }
    }

    fun checkIfFieldsAreValid() {
        _uiState.update {
            it.copy(
                paymentButtonEnabled = if(uiState.value.amount.isNotEmpty()) uiState.value.amount.toDouble() != 0.0 && uiState.value.phoneNumber.isNotEmpty() else uiState.value.amount.isNotEmpty() && uiState.value.phoneNumber.isNotEmpty()
            )
        }
    }


    init {
        loadStartupDetails()
    }
}