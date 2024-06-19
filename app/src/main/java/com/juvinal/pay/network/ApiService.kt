package com.juvinal.pay.network

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
}