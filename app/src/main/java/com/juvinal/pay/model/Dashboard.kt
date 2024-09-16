package com.juvinal.pay.model

import kotlinx.serialization.Serializable

@Serializable
data class DashboardResponseBody(
    val data: DashboardDt,
    val message: String
)

@Serializable
data class DashboardDt(
    val accountSavings: String,
    val loanBalance: Double,
    val guaranteedAmounts: Double,
    val netSavings: String,
    val accountShareCapital: String,
    val loanAmountQualified: String
)