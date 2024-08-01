package com.juvinal.pay.network

import com.juvinal.pay.db.AppDao
import com.juvinal.pay.db.DBRepository
import com.juvinal.pay.model.DashboardResponseBody
import com.juvinal.pay.model.DepositRequestBody
import com.juvinal.pay.model.DepositResponseBody
import com.juvinal.pay.model.LoanRepaymentPayload
import com.juvinal.pay.model.LoanRepaymentResponseBody
import com.juvinal.pay.model.LoanRequestPayload
import com.juvinal.pay.model.LoanRequestResponseBody
import com.juvinal.pay.model.LoanScheduleResponseBody
import com.juvinal.pay.model.LoanTypesResponseBody
import com.juvinal.pay.model.LoansHistoryResponseBody
import com.juvinal.pay.model.MembershipFeePaymentStatusResponseBody
import com.juvinal.pay.model.MembershipFeeRequestBody
import com.juvinal.pay.model.MembershipFeeResponseBody
import com.juvinal.pay.model.TransactionsHistoryResponseBody
import com.juvinal.pay.model.UserLoginRequestBody
import com.juvinal.pay.model.UserLoginResponseBody
import com.juvinal.pay.model.UserRegistrationRequestBody
import com.juvinal.pay.model.UserRegistrationResponseBody
import com.juvinal.pay.model.dbModel.Member
import com.juvinal.pay.model.dbModel.User
import com.juvinal.pay.util.Resource
import kotlinx.coroutines.delay
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response

interface ApiRepository {
    suspend fun registerUser(password: String, userRegistrationRequestBody: UserRegistrationRequestBody): Response<UserRegistrationResponseBody>
    suspend fun loginUser(password: String, userLoginRequestBody: UserLoginRequestBody): Response<UserLoginResponseBody>
    suspend fun membershipFeePayment(membershipFeeRequestBody: MembershipFeeRequestBody): Response<MembershipFeeResponseBody>

    suspend fun checkMemberShipFeePaymentStatus(paymentReference: String): Response<MembershipFeePaymentStatusResponseBody>
    suspend fun checkPaymentStatus(paymentReference: String): Response<MembershipFeePaymentStatusResponseBody>

    suspend fun initiateDeposit(depositRequestBody: DepositRequestBody): Response<DepositResponseBody>
    suspend fun getDashboardDetails(id: Int): Response<DashboardResponseBody>

    suspend fun getTransactionHistory(id: Int): Response<TransactionsHistoryResponseBody>

    suspend fun getLoanTypes(): Response<LoanTypesResponseBody>

    suspend fun requestLoan(
        loanRequestPayload: LoanRequestPayload
    ): Response<LoanRequestResponseBody>

    suspend fun getLoansHistory(
        memNo: String
    ): Response<LoansHistoryResponseBody>

    suspend fun getLoanSchedule(
        loanId: Int
    ): Response<LoanScheduleResponseBody>

    suspend fun payLoan(
        loanRepaymentPayload: LoanRepaymentPayload
    ): Response<LoanRepaymentResponseBody>
}

class ApiRepositoryImpl(private val apiService: ApiService, private val dbRepository: DBRepository) : ApiRepository {
    override suspend fun registerUser(password: String, userRegistrationRequestBody: UserRegistrationRequestBody): Response<UserRegistrationResponseBody> {
        val response = apiService.registerUser(userRegistrationRequestBody = userRegistrationRequestBody)
        if(response.isSuccessful) {
            val user = User(
                user_id = response.body()?.user?.id!!,
                surname = response.body()?.user?.surname!!,
                fname = response.body()?.user?.fname!!,
                lname = response.body()?.user?.lname!!,
                password = password,
                document_type = response.body()?.user?.document_type!!,
                document_no = response.body()?.user?.document_no!!,
                email = response.body()?.user?.email!!,
                phone_no = response.body()?.user?.phone_no!!,
                user_status = 0,
                created_at = response.body()?.user?.created_at!!,
                updated_at = response.body()?.user?.updated_at!!,
                name = response.body()?.user?.name!!,
                uid = response.body()?.user?.uid!!
            )
            val appLaunchStatus = dbRepository.getAppLaunchState(1)
            dbRepository.updateAppLaunchState(
                appLaunchStatus.copy(
                    user_id = response.body()?.user?.id!!
                )
            )
            dbRepository.insertUser(user)
            return response
        } else {
            return response
        }
    }

    override suspend fun loginUser(password: String, userLoginRequestBody: UserLoginRequestBody): Response<UserLoginResponseBody> {
        val response = apiService.loginUser(userLoginRequestBody = userLoginRequestBody)
        if(response.isSuccessful) {
            val user = User(
                user_id = response.body()?.user?.id!!,
                surname = response.body()?.user?.surname!!,
                fname = response.body()?.user?.fname!!,
                lname = response.body()?.user?.lname!!,
                password = password,
                document_type = response.body()?.user?.document_type!!,
                document_no = response.body()?.user?.document_no!!,
                email = response.body()?.user?.email!!,
                phone_no = response.body()?.user?.phone_no!!,
                user_status = response.body()?.user?.user_status!!,
                created_at = response.body()?.user?.created_at!!,
                updated_at = response.body()?.user?.updated_at!!,
                name = response.body()?.user?.name!!,
                uid = response.body()?.user?.uid!!
            )
            val member = Member(
                user_id = response.body()?.member?.user_id!!,
                mem_no = response.body()?.member?.mem_no!!,
                mem_joined_date = response.body()?.member?.mem_joined_date!!,
                mem_status = response.body()?.member?.mem_status!!,
                created_at = response.body()?.member?.created_at!!,
                updated_at = response.body()?.member?.updated_at
            )
            val appLaunchStatus = dbRepository.getAppLaunchState(1)
            dbRepository.updateAppLaunchState(
                appLaunchStatus.copy(
                    user_id = response.body()?.user?.id!!
                )
            )
            dbRepository.insertUser(user)
            dbRepository.insertMember(member)

        }
        return response
    }

    override suspend fun membershipFeePayment(membershipFeeRequestBody: MembershipFeeRequestBody): Response<MembershipFeeResponseBody> = apiService.membershipFeePayment(
        membershipFeeRequestBody = membershipFeeRequestBody
    )

    override suspend fun checkMemberShipFeePaymentStatus(paymentReference: String): Response<MembershipFeePaymentStatusResponseBody> {
        val response = apiService.checkPaymentStatus(paymentReference)
        if(response.isSuccessful) {
            if(response.body()?.status?.lowercase() == "successful"){
                val member = Member(
                    user_id = response.body()?.member?.user_id!!,
                    mem_no = response.body()?.member?.mem_no!!,
                    mem_joined_date = response.body()?.member?.mem_joined_date!!,
                    mem_status = response.body()?.member?.mem_status!!,
                    created_at = response.body()?.member?.created_at!!,
                    updated_at = response.body()?.member?.updated_at
                )
                dbRepository.insertMember(member)
            }
        }
        return response
    }

    override suspend fun checkPaymentStatus(paymentReference: String): Response<MembershipFeePaymentStatusResponseBody> = apiService.checkPaymentStatus(
        paymentReference = paymentReference
    )

    override suspend fun initiateDeposit(depositRequestBody: DepositRequestBody): Response<DepositResponseBody> = apiService.initiateDeposit(
        depositRequestBody = depositRequestBody
    )

    override suspend fun getDashboardDetails(id: Int): Response<DashboardResponseBody> = apiService.getDashboardDetails(
        id = id
    )

    override suspend fun getTransactionHistory(id: Int): Response<TransactionsHistoryResponseBody> = apiService.getTransactionHistory(
        id = id
    )

    override suspend fun getLoanTypes(): Response<LoanTypesResponseBody> = apiService.getLoanTypes()
    override suspend fun requestLoan(
        loanRequestPayload: LoanRequestPayload
    ): Response<LoanRequestResponseBody> = apiService.requestLoan(
        loanRequestPayload = loanRequestPayload
    )

    override suspend fun getLoansHistory(memNo: String): Response<LoansHistoryResponseBody> = apiService.getLoansHistory(
        memNo = memNo
    )

    override suspend fun getLoanSchedule(loanId: Int): Response<LoanScheduleResponseBody> = apiService.getLoanSchedule(
        loanId = loanId
    )

    override suspend fun payLoan(loanRepaymentPayload: LoanRepaymentPayload): Response<LoanRepaymentResponseBody> = apiService.payLoan(
        loanRepaymentPayload = loanRepaymentPayload
    )
}