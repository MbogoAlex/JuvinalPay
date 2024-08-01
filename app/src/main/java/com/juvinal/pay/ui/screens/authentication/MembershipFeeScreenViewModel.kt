package com.juvinal.pay.ui.screens.authentication

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.datastore.PaymentReferenceDSModel
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.MembershipFeeRequestBody
import com.juvinal.pay.model.dbModel.Member
import com.juvinal.pay.model.dbModel.UserDetails
import com.juvinal.pay.network.ApiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MembershipFeeScreenUiState(
    val msisdn: String = "",
    val paymentReference: String? = null,
    val userDetails: UserDetails = UserDetails(),
    val paymentButtonEnabled: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class MembershipFeeScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository,
    private val dbRepository: DBRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MembershipFeeScreenUiState())
    val uiState: StateFlow<MembershipFeeScreenUiState> = _uiState.asStateFlow()

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> = _isConnected

    private val members = mutableStateListOf<Member>()
    private var paymentSuccess = false

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

    fun loadUserData() {
        viewModelScope.launch {
            val userId = dbRepository.getAppLaunchState(1).user_id
            if(userId != null) {
                val user = dbRepository.getUser(userId).first()
                val member = dbRepository.getMember(userId).first()
                val userDetails = UserDetails(
                    user = user,
                    member = member
                )
                _uiState.update {
                    it.copy(
                        userDetails = userDetails,
                        msisdn = user.phone_no
                    )
                }
            }

        }
    }

    fun loadPaymentData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    paymentReference = dsRepository.paymentDSDetails.first().memberFeePaymentReference
                )
            }
        }
    }

    fun updatePhoneNo(phoneNo: String) {
        _uiState.update {
            it.copy(
                msisdn = phoneNo
            )
        }
    }

    fun payMembershipFee() {
        Log.i("PAYING_MEMBERSHIP_FEE", "PAYING_MEMBERSHIP_FEE")
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        val membershipFeeRequestBody = MembershipFeeRequestBody(
            uid = uiState.value.userDetails.user!!.uid,
            msisdn = if(uiState.value.msisdn.startsWith("254")) uiState.value.msisdn.replace("254", "0") else uiState.value.msisdn,
            payment_purpose = "MEMBER_REGISTRATION_FEE"
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.membershipFeePayment(membershipFeeRequestBody)
                if(response.isSuccessful) {
                    val paymentDSModel = PaymentReferenceDSModel(
                        memberFeePaymentReference = response.body()?.paymentReference!!,
                        depositPaymentReference = null
                    )
                    dsRepository.savePaymentData(paymentDSModel)
                    _uiState.update {
                        it.copy(
                            paymentReference = response.body()?.paymentReference!!
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            loadingStatus = LoadingStatus.FAIL
                        )
                    }
                    Log.e("MEMBERSHIP_FEE_ERROR_RESPONSE", response.toString())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        loadingStatus = LoadingStatus.FAIL
                    )
                }
                Log.e("MEMBERSHIP_FEE_ERROR_EXCEPTION", e.toString())
            }
        }
    }

    fun checkPaymentStatus() {
        Log.i("REFERENCE_ID:", uiState.value.paymentReference!!)
        viewModelScope.launch {
            try {
               val response = apiRepository.checkMemberShipFeePaymentStatus(uiState.value.paymentReference!!)
                Log.i("RESPONSE:", response.toString())
               if(response.isSuccessful) {
                   if(response.body()?.status?.lowercase() == "successful") {
                       members.addAll(dbRepository.getMembers().first())
                       when(members.isNotEmpty() && !paymentSuccess) {
                           true -> {
                               paymentSuccess = true
                               _uiState.update {
                                   it.copy(
                                       loadingStatus = LoadingStatus.SUCCESS
                                   )
                               }
                           }
                           false -> {
                               delay(2000L)
                               members.addAll(dbRepository.getMembers().first())
                           }
                       }

                   } else {
                       _uiState.update {
                           it.copy(
                               loadingStatus = LoadingStatus.FAIL
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

    fun checkIfRequiredFieldsAreFilled() {
        _uiState.update {
            it.copy(
                paymentButtonEnabled = _uiState.value.msisdn.isNotEmpty()
            )
        }
    }

    init {
        loadUserData()
        loadPaymentData()
    }

}