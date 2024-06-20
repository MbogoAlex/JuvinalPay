package com.juvinal.pay.ui.screens.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juvinal.pay.LoadingStatus
import com.juvinal.pay.UserDetails
import com.juvinal.pay.datastore.DSRepository
import com.juvinal.pay.model.MembershipFeeRequestBody
import com.juvinal.pay.network.ApiRepository
import com.juvinal.pay.toUserDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MembershipFeeScreenUiState(
    val msisdn: String = "",
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
                            loadingStatus = LoadingStatus.SUCCESS
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