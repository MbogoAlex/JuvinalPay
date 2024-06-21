package com.juvinal.pay

import com.juvinal.pay.datastore.UserDSModel


data class UserDetails(
    val id: Int? = null,
    val name: String = "",
    val uid: String = "",
    val mem_no: String? = "",
    val mem_joined_date: String? = null,
    val mem_status: Int? = null,
    val mem_registered: Boolean = false,
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
    mem_no = mem_no,
    mem_joined_date = mem_joined_date,
    mem_status = mem_status,
    mem_registered = mem_registered,
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