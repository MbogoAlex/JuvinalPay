package com.juvinal.pay.model

import androidx.compose.ui.Modifier
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

@Serializable
data class UserLoginRequestBody(
    val document_no: String,
    val password: String
)
@Serializable
data class UserLoginResponseBody(
    val status: String,
    val user: UserLoginDT
)

@Serializable
data class UserLoginDT(
    val id: Int,
    val surname: String,
    val fname: String,
    val lname: String,
    val document_type: String,
    val document_no: String,
    val email: String,
    val phone_no: String,
    val profile_avatar: String?,
    val profile_bg: String?,
    val user_city: String?,
    val user_status: Int,
    val sys_user: Int,
    val user_country: String?,
    val county_id: Int?,
    val sub_county_id: Int?,
    val ward_id: Int?,
    val email_verified_at: String?,
    val created_at: String,
    val updated_at: String,
    val name: String,
    val uid: String,
    val docType: DocType
)