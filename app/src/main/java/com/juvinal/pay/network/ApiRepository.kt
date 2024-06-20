package com.juvinal.pay.network

import com.juvinal.pay.model.MembershipFeeRequestBody
import com.juvinal.pay.model.MembershipFeeResponseBody
import com.juvinal.pay.model.UserLoginRequestBody
import com.juvinal.pay.model.UserLoginResponseBody
import com.juvinal.pay.model.UserRegistrationRequestBody
import com.juvinal.pay.model.UserRegistrationResponseBody
import retrofit2.Response

interface ApiRepository {
    suspend fun registerUser(userRegistrationRequestBody: UserRegistrationRequestBody): Response<UserRegistrationResponseBody>
    suspend fun loginUser(userLoginRequestBody: UserLoginRequestBody): Response<UserLoginResponseBody>
    suspend fun membershipFeePayment(membershipFeeRequestBody: MembershipFeeRequestBody): Response<MembershipFeeResponseBody>
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
}