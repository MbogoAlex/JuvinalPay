package com.juvinal.pay.network

import com.juvinal.pay.model.DepositRequestBody
import com.juvinal.pay.model.DepositResponseBody
import com.juvinal.pay.model.MembershipFeePaymentStatusResponseBody
import com.juvinal.pay.model.MembershipFeeRequestBody
import com.juvinal.pay.model.MembershipFeeResponseBody
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
}