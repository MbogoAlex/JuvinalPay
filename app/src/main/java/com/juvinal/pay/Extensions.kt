package com.juvinal.pay

import com.juvinal.pay.datastore.UserDSModel

data class UserDetails(
    val id: Int? = null,
    val name: String = "",
    val uid: String = "",
    val surname: String = "",
    val fname: String = "",
    val lname: String = "",
    val document_type: String = "",
    val document_no: String = "",
    val email: String = "",
    val phone_no: String = "",
    val password: String = "",
    val created_at: String = "",
    val updated_at: String = "",
)

fun UserDSModel.toUserDetails(): UserDetails = UserDetails(
    id =  id,
    name = name,
    uid = uid,
    surname = surname,
    fname = fname,
    lname = lname,
    document_type = document_type,
    document_no = document_no,
    email = email,
    phone_no = phone_no,
    password = password,
    created_at = created_at,
    updated_at = updated_at,
)