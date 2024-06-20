package com.juvinal.pay.network

import com.juvinal.pay.model.UserLoginRequestBody
import com.juvinal.pay.model.UserLoginResponseBody
import com.juvinal.pay.model.UserRegistrationRequestBody
import com.juvinal.pay.model.UserRegistrationResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("createuser")
    suspend fun registerUser(
        @Body userRegistrationRequestBody: UserRegistrationRequestBody
    ): Response<UserRegistrationResponseBody>

    @POST("login")
    suspend fun loginUser(
        @Body userLoginRequestBody: UserLoginRequestBody
    ): Response<UserLoginResponseBody>
}