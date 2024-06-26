package com.juvinal.pay.network

import com.juvinal.pay.model.DashboardResponseBody
import com.juvinal.pay.model.DepositRequestBody
import com.juvinal.pay.model.DepositResponseBody
import com.juvinal.pay.model.MembershipFeePaymentStatusResponseBody
import com.juvinal.pay.model.MembershipFeeRequestBody
import com.juvinal.pay.model.MembershipFeeResponseBody
import com.juvinal.pay.model.TransactionsHistoryResponseBody
import com.juvinal.pay.model.UserLoginRequestBody
import com.juvinal.pay.model.UserLoginResponseBody
import com.juvinal.pay.model.UserRegistrationRequestBody
import com.juvinal.pay.model.UserRegistrationResponseBody
import retrofit2.Response

interface ApiRepository {
    suspend fun registerUser(userRegistrationRequestBody: UserRegistrationRequestBody): Response<UserRegistrationResponseBody>
    suspend fun loginUser(userLoginRequestBody: UserLoginRequestBody): Response<UserLoginResponseBody>
    suspend fun membershipFeePayment(membershipFeeRequestBody: MembershipFeeRequestBody): Response<MembershipFeeResponseBody>

    suspend fun checkPaymentStatus(paymentReference: String): Response<MembershipFeePaymentStatusResponseBody>

    suspend fun initiateDeposit(depositRequestBody: DepositRequestBody): Response<DepositResponseBody>
    suspend fun getDashboardDetails(id: Int): Response<DashboardResponseBody>

    suspend fun getTransactionHistory(id: Int): Response<TransactionsHistoryResponseBody>
}

class ApiRepositoryImpl(private val apiService: ApiService) : ApiRepository {
    override suspend fun registerUser(userRegistrationRequestBody: UserRegistrationRequestBody): Response<UserRegistrationResponseBody> = apiService.registerUser(
        userRegistrationRequestBody = userRegistrationRequestBody
    )

    override suspend fun loginUser(userLoginRequestBody: UserLoginRequestBody): Response<UserLoginResponseBody> = apiService.loginUser(
        userLoginRequestBody = userLoginRequestBody
    )

    override suspend fun membershipFeePayment(membershipFeeRequestBody: MembershipFeeRequestBody): Response<MembershipFeeResponseBody> = apiService.membershipFeePayment(
        membershipFeeRequestBody = membershipFeeRequestBody
    )

    override suspend fun checkPaymentStatus(paymentReference: String): Response<MembershipFeePaymentStatusResponseBody> = apiService.checkPaymentStatus(
        paymentReference = paymentReference
    )

    override suspend fun initiateDeposit(depositRequestBody: DepositRequestBody): Response<DepositResponseBody> = apiService.initiateDeposit(
        depositRequestBody = depositRequestBody
    )

    override suspend fun getDashboardDetails(id: Int): Response<DashboardResponseBody> = apiService.getDashboardDetails(
        id = id
    )

    override suspend fun getTransactionHistory(id: Int): Response<TransactionsHistoryResponseBody> = apiService.getTransactionHistory(
        id = id
    )
}