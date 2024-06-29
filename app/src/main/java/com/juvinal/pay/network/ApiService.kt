package com.juvinal.pay.network

import com.juvinal.pay.model.DashboardResponseBody
import com.juvinal.pay.model.DepositRequestBody
import com.juvinal.pay.model.DepositResponseBody
import com.juvinal.pay.model.LoanRequestPayload
import com.juvinal.pay.model.LoanRequestResponseBody
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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("createuser")
    suspend fun registerUser(
        @Body userRegistrationRequestBody: UserRegistrationRequestBody
    ): Response<UserRegistrationResponseBody>

    @POST("login")
    suspend fun loginUser(
        @Body userLoginRequestBody: UserLoginRequestBody
    ): Response<UserLoginResponseBody>

    @POST("member/paymembershipfee")
    suspend fun membershipFeePayment(
        @Body membershipFeeRequestBody: MembershipFeeRequestBody
    ): Response<MembershipFeeResponseBody>

    @GET("payments/{paymentReference}/findstatus")
    suspend fun checkPaymentStatus(
        @Path("paymentReference") paymentReference: String
    ): Response<MembershipFeePaymentStatusResponseBody>

    @POST("member/initiatememberdeposit")
    suspend fun initiateDeposit(
        @Body depositRequestBody: DepositRequestBody
    ): Response<DepositResponseBody>
    @GET("dashboard/{id}")
    suspend fun getDashboardDetails(@Path("id") id: Int): Response<DashboardResponseBody>
    @GET("transactionhistory/{id}")
    suspend fun getTransactionHistory(@Path("id") id: Int): Response<TransactionsHistoryResponseBody>
    @GET("loantypes")
    suspend fun getLoanTypes(): Response<LoanTypesResponseBody>
    @POST("createloan")
    suspend fun requestLoan(@Body loanRequestPayload: LoanRequestPayload): Response<LoanRequestResponseBody>
    @GET("loans/{memNo}")
    suspend fun getLoansHistory(@Path("memNo") memNo: String): Response<LoansHistoryResponseBody>
}