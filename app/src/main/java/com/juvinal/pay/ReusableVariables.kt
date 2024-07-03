package com.juvinal.pay

import android.os.Build
import androidx.annotation.RequiresApi
import com.juvinal.pay.model.LoanHistoryDt
import com.juvinal.pay.model.LoanScheduleDT
import com.juvinal.pay.model.LoanStatusCode
import com.juvinal.pay.model.LoanTypeDt
import java.time.format.DateTimeFormatter

val documentTypes = listOf(
    DocumentTypeItem(
        name = "National Identification",
        documentType = DocumentType.NATIONAL_ID
    ),
    DocumentTypeItem(
        name = "Passport",
        documentType = DocumentType.PASSPORT
    ),
    DocumentTypeItem(
        name = "Alien ID",
        documentType = DocumentType.ALIEN_ID
    ),
)

@RequiresApi(Build.VERSION_CODES.O)
val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")

val loanTypeDt = LoanTypeDt(
    id = 1,
    loan_type_name = "BUSINESS LOAN",
)

val loanTypes = listOf(
    LoanTypeDt(
        id = 1,
        loan_type_name = "BUSINESS LOAN",
    ),
    LoanTypeDt(
        id = 1,
        loan_type_name = "BUSINESS LOAN",
    ),
    LoanTypeDt(
        id = 1,
        loan_type_name = "BUSINESS LOAN",
    ),
)


val loanStatusCode = LoanStatusCode(
    name = "NEW",
    desc = "New Transaction",
    status = 1,
    class_code = "primary",
    is_loan = 1
)

val loanHistoryDt = LoanHistoryDt(
    id =   1,
    loan_ref = "LN307732D8",
    loan_req_amount = "20.00",
    loan_approved_amount = "0.00",
    loan_interest = "20.00",
    loan_outstanding_bal = "0.00",
    loan_outstanding_int = "0.00",
    loan_total_principal = "20.00",
    loan_disbursed_date = null,
    loan_status = "NEW",
    loanStatusCode = loanStatusCode
)

val loanHistory = listOf(
    LoanHistoryDt(
        id =   1,
        loan_ref = "LN307732D8",
        loan_req_amount = "20.00",
        loan_approved_amount = "0.00",
        loan_interest = "20.00",
        loan_outstanding_bal = "0.00",
        loan_outstanding_int = "0.00",
        loan_total_principal = "20.00",
        loan_disbursed_date = null,
        loan_status = "NEW",
        loanStatusCode = loanStatusCode
    ),
    LoanHistoryDt(
        id =   1,
        loan_ref = "LN307732D8",
        loan_req_amount = "20.00",
        loan_approved_amount = "0.00",
        loan_interest = "20.00",
        loan_outstanding_bal = "0.00",
        loan_outstanding_int = "0.00",
        loan_total_principal = "20.00",
        loan_disbursed_date = "2024-05-22T11:14:57.236022",
        loan_status = "NEW",
        loanStatusCode = loanStatusCode
    )
)

val loanSchedule = LoanScheduleDT(
    schedule_pay_date = "31-Jul-2024",
    schedule_total = "10.00",
    schedule_total_paid = "0.00",
    schedule_total_balance = "10.00",
    schedule_status = 0
)

val loanScheduleList = listOf(
    LoanScheduleDT(
        schedule_pay_date = "31-Jul-2024",
        schedule_total = "10.00",
        schedule_total_paid = "0.00",
        schedule_total_balance = "10.00",
        schedule_status = 0
    ),
    LoanScheduleDT(
        schedule_pay_date = "31-Jul-2024",
        schedule_total = "10.00",
        schedule_total_paid = "0.00",
        schedule_total_balance = "10.00",
        schedule_status = 0
    ),
    LoanScheduleDT(
        schedule_pay_date = "31-Jul-2024",
        schedule_total = "10.00",
        schedule_total_paid = "0.00",
        schedule_total_balance = "10.00",
        schedule_status = 0
    ),
    LoanScheduleDT(
        schedule_pay_date = "31-Jul-2024",
        schedule_total = "10.00",
        schedule_total_paid = "0.00",
        schedule_total_balance = "10.00",
        schedule_status = 0
    ),
    LoanScheduleDT(
        schedule_pay_date = "31-Jul-2024",
        schedule_total = "10.00",
        schedule_total_paid = "0.00",
        schedule_total_balance = "10.00",
        schedule_status = 0
    )
)

