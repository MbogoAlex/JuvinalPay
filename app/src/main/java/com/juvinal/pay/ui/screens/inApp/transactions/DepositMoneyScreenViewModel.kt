package com.juvinal.pay.ui.screens.inApp.transactions

import android.util.Log
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
    val phoneNumber: String = "",
    val statusCheckMessage: String = "",
    val depositButtonEnabled: Boolean = false,
    val showSuccessDialog: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class DepositMoneyScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(DepositMoneyScreenUiState())
    val uiState: StateFlow<DepositMoneyScreenUiState> = _uiState.asStateFlow()

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
                amount = amount
            )
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
                        depositPaymentReference = response.body()?.paymentReference
                    )
                    dsRepository.savePaymentData(paymentDSModel)
                    _uiState.update {
                        it.copy(
                            depositPaymentReferenceId = response.body()?.paymentReference,
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
    }
}