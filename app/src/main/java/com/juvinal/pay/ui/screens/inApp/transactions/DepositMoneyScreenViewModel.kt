package com.juvinal.pay.ui.screens.inApp.transactions

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.datastore.PaymentReferenceDSModel
import com.juvinal.pay.datastore.UserDSModel
import com.juvinal.pay.model.DepositRequestBody
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DepositMoneyScreenUiState(
    val memberFeeReferenceId: String? = "",
    val depositPaymentReferenceId: String? = "",
    val userDetails: UserDetails = UserDetails(),
    val amount: String = "",
    val amountDeposited: Double = 0.0,
    val phoneNumber: String = "",
    val statusCheckMessage: String = "",
    val depositButtonEnabled: Boolean = false,
    val showSuccessDialog: Boolean = false,
    val accountSavings: Double = 0.0,
    val loanBalance: Double = 0.0,
    val guaranteedAmounts: Double = 0.0,
    val netSavings: Double = 0.0,
    val accountShareCapital: Double = 0.0,
    val loanAmountQualified: Double = 0.0,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class DepositMoneyScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DepositMoneyScreenUiState())
    val uiState: StateFlow<DepositMoneyScreenUiState> = _uiState.asStateFlow()

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

    fun loadUserDetails() {
        viewModelScope.launch {
            dsRepository.userDSDetails.collect(){dsUserDetails->
                _uiState.update {
                    it.copy(
                        userDetails = dsUserDetails.toUserDetails(),
                        phoneNumber = dsUserDetails.phone_no
                    )
                }
            }
        }
    }

    fun loadPaymentData() {
        viewModelScope.launch {
            dsRepository.paymentDSDetails.collect(){paymentDsDetails->
                _uiState.update {
                    it.copy(
                        memberFeeReferenceId = paymentDsDetails.memberFeePaymentReference,
                        depositPaymentReferenceId = paymentDsDetails.depositPaymentReference
                    )
                }
            }
        }
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
                amountDeposited = if(amount.isNotEmpty()) amount.toDouble() else 0.0
            )
        }
    }

    fun getDashboardDetails() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getDashboardDetails(uiState.value.userDetails.id!!)
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            accountSavings = response.body()?.data?.accountSavings!!.toDouble(),
                            loanBalance = response.body()?.data?.loanBalance!!,
                            guaranteedAmounts = response.body()?.data?.guaranteedAmounts!!,
                            netSavings = response.body()?.data?.netSavings!!,
                            accountShareCapital = response.body()?.data?.accountShareCapital!!.toDouble(),
                            loanAmountQualified = response.body()?.data?.loanAmountQualified!!.toDouble(),
                        )
                    }
                } else {
                    Log.e("DASHBOARD_ERROR_RESPONSE", response.toString())
                }

            } catch (e: Exception) {
                Log.e("DASHBOARD_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun initiateDeposit() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        val depositRequestBody = DepositRequestBody(
            uid = uiState.value.userDetails.uid,
            msisdn = uiState.value.phoneNumber,
            payment_purpose = "MEMBER_DEPOSIT",
            amount = uiState.value.amount.toDouble()
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.initiateDeposit(depositRequestBody)
                Log.i("RESPONSE:", response.body().toString())
                if(response.isSuccessful) {
                    val paymentDSModel = PaymentReferenceDSModel(
                        memberFeePaymentReference = uiState.value.memberFeeReferenceId,
                        depositPaymentReference = response.body()?.paymentReference,
                    )
                    dsRepository.savePaymentData(paymentDSModel)
                    _uiState.update {
                        it.copy(
                            depositPaymentReferenceId = response.body()?.paymentReference,
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
        Log.i("REFERENCE_ID:", uiState.value.depositPaymentReferenceId!!)
        viewModelScope.launch {
            try {
                val response = apiRepository.checkPaymentStatus(uiState.value.depositPaymentReferenceId!!)
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
                        Log.e("PAYMENT_STATUS_CHECK_NOT_SUCCESS", response.body()?.status.toString())
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
                depositButtonEnabled = if(uiState.value.amount.isNotEmpty()) uiState.value.amount.toDouble() != 0.0 && uiState.value.phoneNumber.isNotEmpty() else uiState.value.amount.isNotEmpty() && uiState.value.phoneNumber.isNotEmpty()
            )
        }
    }

    init {
        loadUserDetails()
        loadPaymentData()
        getDashboardDetails()
    }
}