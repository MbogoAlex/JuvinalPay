package com.juvinal.pay.ui.screens.inApp.transactions.loan

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.loanTypeDt
import com.juvinal.pay.model.LoanRequestPayload
import com.juvinal.pay.model.LoanTypeDt
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.resusableFunctions.formatMoneyValue
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class RequestLoanScreenUiState(
    val userDetails: UserDetails = UserDetails(),
    val amount: String = "",
    val requestedAmount: String = "",
    val loanPurpose: String = "",
    val type: LoanTypeDt = loanTypeDt,
    val loanTypes: List<LoanTypeDt> = emptyList(),
    val accountSavings: Double = 0.0,
    val loanBalance: Double = 0.0,
    val guaranteedAmounts: Double = 0.0,
    val netSavings: Double = 0.0,
    val accountShareCapital: Double = 0.0,
    val loanAmountQualified: Double = 0.0,
    val requestButtonEnabled: Boolean = false,
    val requestResponseMessage: String = "",
    val daysRemaining: Int = 0,
    val eligible: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
@RequiresApi(Build.VERSION_CODES.O)
class RequestLoanScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(RequestLoanScreenUiState())
    val uiState: StateFlow<RequestLoanScreenUiState> = _uiState.asStateFlow()

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadStartupData(){
        // Parse the created_at field as OffsetDateTime, then convert to LocalDateTime if needed

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    userDetails = dsRepository.userDSDetails.first().toUserDetails(),

                )
            }

            val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val createdAt = OffsetDateTime.parse(uiState.value.userDetails.created_at, formatter)
            val localCreatedAt = createdAt.toLocalDateTime()

            val sixMonthsLater = localCreatedAt.plusMonths(6)
            val now = LocalDateTime.now()

            val monthsRemaining = ChronoUnit.MONTHS.between(now, sixMonthsLater)
            val daysRemaining = ChronoUnit.DAYS.between(now, sixMonthsLater)

            _uiState.update {
                it.copy(
                    daysRemaining = daysRemaining.toInt(),
                    eligible = daysRemaining < 1
                )
            }

            getDashboardDetails()
            getLoanTypes()



            Log.d("MONTHS_REMAINING", monthsRemaining.toString())
            Log.d("DAYS_REMAINING", daysRemaining.toString())
        }


    }

    fun updateAmount(amount: String) {
        _uiState.update {
            it.copy(
                amount = amount,
                requestedAmount = amount
            )
        }
    }

    fun updateLoanType(loanTypeDt: LoanTypeDt) {
        _uiState.update {
            it.copy(
                type = loanTypeDt
            )
        }
    }

    fun updateLoanPurpose(loanPurpose: String) {
        _uiState.update {
            it.copy(
                loanPurpose = loanPurpose
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

    fun getLoanTypes() {
        viewModelScope.launch {
            try {
                val response = apiRepository.getLoanTypes()
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            loanTypes = response.body()?.data!!
                        )
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun requestLoan() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        val loanRequestPayload = LoanRequestPayload(
            mem_no = uiState.value.userDetails.mem_no!!,
            loan_type_id = uiState.value.type.id,
            loan_req_amount = uiState.value.amount.toDouble(),
            loan_purpose = uiState.value.loanPurpose,
            uid = uiState.value.userDetails.uid
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.requestLoan(
                    loanRequestPayload = loanRequestPayload
                )
                if(response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            requestResponseMessage = response.body()?.message!!,
                            loadingStatus = LoadingStatus.SUCCESS
                        )
                    }
                } else {
                    Log.e("ERROR_MSG", response.toString())
                    var errMsg = ""
                    if(uiState.value.amount.toDouble() > uiState.value.loanAmountQualified) {
                        errMsg = "Your loan limit is ${formatMoneyValue(uiState.value.loanAmountQualified)}"
                    } else if(uiState.value.loanBalance == 0.00) {
                        errMsg = "You have an unapproved loan"
                    } else {
                        errMsg = "You have an unpaid loan of ${formatMoneyValue(uiState.value.loanBalance)}"
                    }
                    _uiState.update {
                        it.copy(
                            requestResponseMessage = errMsg,
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                }
            }catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        requestResponseMessage = "Failed. Try again later",
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("LOAN_REQUEST_ERROR_EXCEPTION", e.toString())
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

    fun checkIfRequiredFieldsAreFilled() {
        _uiState.update {
            it.copy(
                requestButtonEnabled = uiState.value.amount.isNotEmpty() && uiState.value.amount.toDouble() != 0.0 &&
                        uiState.value.loanPurpose.isNotEmpty()
            )
        }
    }

    init {
        loadStartupData()
    }
}