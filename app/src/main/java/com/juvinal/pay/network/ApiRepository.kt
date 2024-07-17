package com.juvinal.pay.network

import com.juvinal.pay.model.DashboardResponseBody
import com.juvinal.pay.model.DepositRequestBody
import com.juvinal.pay.model.DepositResponseBody
import com.juvinal.pay.model.LoanRepaymentPayload
import com.juvinal.pay.model.LoanRepaymentResponseBody
import com.juvinal.pay.model.LoanRequestPayload
import com.juvinal.pay.model.LoanRequestResponseBody
import com.juvinal.pay.model.LoanScheduleResponseBody
import com.juvinal.pay.model.LoanTypesResponseBody
import com.juvinal.pay.model.LoansHistoryResponseBody
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

    suspend fun getLoanTypes(): Response<LoanTypesResponseBody>

    suspend fun requestLoan(
        loanRequestPayload: LoanRequestPayload
    ): Response<LoanRequestResponseBody>

    suspend fun getLoansHistory(
        memNo: String
    ): Response<LoansHistoryResponseBody>

    suspend fun getLoanSchedule(
        loanId: Int
    ): Response<LoanScheduleResponseBody>

    suspend fun payLoan(
        loanRepaymentPayload: LoanRepaymentPayload
    ): Response<LoanRepaymentResponseBody>
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

    override suspend fun getLoanTypes(): Response<LoanTypesResponseBody> = apiService.getLoanTypes()
    override suspend fun requestLoan(
        loanRequestPayload: LoanRequestPayload
    ): Response<LoanRequestResponseBody> = apiService.requestLoan(
        loanRequestPayload = loanRequestPayload
    )

    override suspend fun getLoansHistory(memNo: String): Response<LoansHistoryResponseBody> = apiService.getLoansHistory(
        memNo = memNo
    )

    override suspend fun getLoanSchedule(loanId: Int): Response<LoanScheduleResponseBody> = apiService.getLoanSchedule(
        loanId = loanId
    )

    override suspend fun payLoan(loanRepaymentPayload: LoanRepaymentPayload): Response<LoanRepaymentResponseBody> = apiService.payLoan(
        loanRepaymentPayload = loanRepaymentPayload
    )
}