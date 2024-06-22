package com.juvinal.pay.model

import kotlinx.serialization.Serializable

@Serializable
data class DepositRequestBody(
    val uid: String,
    val msisdn: String,
    val payment_purpose: String,
    val amount: Double,
)

@Serializable
data class DepositResponseBody(
    val paymentReference: String,
    val gatewayTransactionID: String,
    val paymentInstructions: String,
    val statusCode: String
)

@Serializable
data class DepositStatusResponseBody(
    val status: String,
    val message: String,
    val member: MemberDT?
)