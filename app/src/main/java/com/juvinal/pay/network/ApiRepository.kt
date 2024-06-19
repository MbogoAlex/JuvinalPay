package com.juvinal.pay.network

import com.juvinal.pay.model.UserRegistrationRequestBody
import com.juvinal.pay.model.UserRegistrationResponseBody
import retrofit2.Response

interface ApiRepository {
    suspend fun registerUser(userRegistrationRequestBody: UserRegistrationRequestBody): Response<UserRegistrationResponseBody>
}

class ApiRepositoryImpl(private val apiService: ApiService) : ApiRepository {
    override suspend fun registerUser(userRegistrationRequestBody: UserRegistrationRequestBody): Response<UserRegistrationResponseBody> = apiService.registerUser(
        userRegistrationRequestBody = userRegistrationRequestBody
    )
}