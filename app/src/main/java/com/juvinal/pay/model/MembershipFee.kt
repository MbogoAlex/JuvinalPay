package com.juvinal.pay.model

import kotlinx.serialization.Serializable

@Serializable
data class MembershipFeeRequestBody(
    val uid: String,
    val msisdn: String,
    val payment_purpose: String
)
@Serializable
data class MembershipFeeResponseBody(
    val paymentReference: String,
    val gatewayTransactionID: String,
    val paymentInstructions: String,
    val statusCode: String
)

@Serializable
data class MembershipFeePaymentStatusResponseBody(
    val status: String,
    val message: String,
    val member: MemberDT?
)