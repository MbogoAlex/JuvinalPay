package com.juvinal.pay.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequestBody(
    val surname: String,
    val fname: String,
    val lname: String,
    val document_type: String,
    val document_no: String,
    val phone_no: String,
    val email: String,
    val password: String,
    val password_confirmation: String
)
@Serializable
data class UserRegistrationResponseBody(
    val message: String,
    val user: UserDt
)
@Serializable
data class UserDt(
    val surname: String,
    val fname: String,
    val lname: String,
    val document_type: String,
    val document_no: String,
    val phone_no: String,
    val email: String,
    val updated_at: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val uid: String,
    val docType: DocType
)
@Serializable
data class DocType(
    val name: String,
    val desc: String,
    val status: Int
)