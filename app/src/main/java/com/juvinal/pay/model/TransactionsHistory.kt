package com.juvinal.pay.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionsHistoryResponseBody(
    val data: List<TransactionHistoryData>,
    val message: String,
)
@Serializable
data class TransactionHistoryData(
    val acct_no: String,
    val tran_type: String,
    val tran_ref: String,
    val tran_amt: String,
    val tran_status: String,
    val payment_mode: String,
    val share_month: String,
    val tranTypeEntry: TransactionTypeEntry,
    val tranStatusCode: TransactionStatusCode,
)
@Serializable
data class TransactionTypeEntry(
    val name: String,
    val desc: String,
    val status: Int
)
@Serializable
data class TransactionStatusCode(
    val name: String,
    val desc: String,
    val status: Int,
    val class_code: String,
    val is_loan: Int
)