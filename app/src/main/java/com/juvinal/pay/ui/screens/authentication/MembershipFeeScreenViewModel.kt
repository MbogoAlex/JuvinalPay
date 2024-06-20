package com.juvinal.pay.ui.screens.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.datastore.UserDSModel
import com.juvinal.pay.model.MembershipFeeRequestBody
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MembershipFeeScreenUiState(
    val msisdn: String = "",
    val paymentReference: String = "",
    val userDetails: UserDetails = UserDetails(),
    val paymentButtonEnabled: Boolean = false,
    val loadingStatus: LoadingStatus = LoadingStatus.INITIAL
)
class MembershipFeeScreenViewModel(
    private val apiRepository: ApiRepository,
    private val dsRepository: DSRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MembershipFeeScreenUiState())
    val uiState: StateFlow<MembershipFeeScreenUiState> = _uiState.asStateFlow()

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
    }

    fun updatePhoneNo(phoneNo: String) {
        _uiState.update {
            it.copy(
                msisdn = phoneNo
            )
        }
    }

    fun payMembershipFee() {
        _uiState.update {
            it.copy(
                loadingStatus = LoadingStatus.LOADING
            )
        }
        val membershipFeeRequestBody = MembershipFeeRequestBody(
            uid = uiState.value.userDetails.uid,
            msisdn = uiState.value.msisdn,
            payment_purpose = "MEMBER_REGISTRATION_FEE"
        )
        viewModelScope.launch {
            try {
                val response = apiRepository.membershipFeePayment(membershipFeeRequestBody)
                if(response.isSuccessful) {
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
        viewModelScope.launch {
            try {
               val response = apiRepository.checkMembershipFeePaymentStatus(uiState.value.paymentReference)
               if(response.isSuccessful) {
                   if(response.body()?.status?.lowercase() == "successful") {
                       val dsUserDSModel = UserDSModel(
                           id = uiState.value.userDetails.id,
                           name = uiState.value.userDetails.name,
                           uid = uiState.value.userDetails.uid,
                           mem_no = response.body()?.member?.mem_no,
                           mem_joined_date = response.body()?.member?.mem_joined_date,
                           mem_status = response.body()?.member?.mem_status,
                           mem_registered = response.body()?.member != null,
                           surname = uiState.value.userDetails.surname,
                           fname = uiState.value.userDetails.fname,
                           lname = uiState.value.userDetails.lname,
                           document_type = uiState.value.userDetails.document_type,
                           document_no = uiState.value.userDetails.document_no,
                           email = uiState.value.userDetails.email,
                           phone_no = uiState.value.userDetails.phone_no,
                           password = uiState.value.userDetails.password,
                           created_at = uiState.value.userDetails.created_at,
                           updated_at = uiState.value.userDetails.updated_at
                       )
                       dsRepository.saveUserDetails(dsUserDSModel)
                       _uiState.update {
                           it.copy(
                               loadingStatus = LoadingStatus.SUCCESS
                           )
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
        loadStartupData()
    }

}