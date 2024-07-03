package com.juvinal.pay.model

import kotlinx.serialization.Serializable
@Serializable
data class LoanRequestPayload(
    val mem_no: String,
    val loan_type_id: Int,
    val loan_req_amount: Double,
    val loan_purpose: String,
    val uid: String
)
@Serializable
data class LoanRequestResponseBody(
    val status: String,
    val message: String
)

@Serializable
data class LoanTypesResponseBody(
    val data: List<LoanTypeDt>,
    val message: String,
)
@Serializable
data class LoanTypeDt(
    val id: Int,
    val loan_type_name: String,
)
@Serializable
data class LoansHistoryResponseBody(
    val message: String,
    val data: List<LoanHistoryDt>
)
@Serializable
data class LoanHistoryDt(
    val id: Int,
    val loan_ref: String,
    val loan_req_amount: String,
    val loan_approved_amount: String,
    val loan_interest: String,
    val loan_outstanding_bal: String,
    val loan_outstanding_int: String,
    val loan_total_principal: String,
    val loan_disbursed_date: String?,
    val loan_status: String,
    val loanStatusCode: LoanStatusCode
)
@Serializable
data class LoanStatusCode(
    val name: String,
    val desc: String,
    val status: Int,
    val class_code: String,
    val is_loan: Int
)
@Serializable
data class LoanScheduleResponseBody(
    val message: String,
    val data: List<LoanScheduleDT>
)

@Serializable
data class LoanScheduleDT(
    val schedule_pay_date: String,
    val schedule_total: String,
    val schedule_total_paid: String,
    val schedule_total_balance: String,
    val schedule_status: Int
)