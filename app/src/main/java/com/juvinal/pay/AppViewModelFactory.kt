package com.juvinal.pay

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.juvinal.pay.ui.screens.SplashScreenViewModel
import com.juvinal.pay.ui.screens.WelcomeScreenViewModel
import com.juvinal.pay.ui.screens.authentication.LoginScreenViewModel
import com.juvinal.pay.ui.screens.authentication.MembershipFeeScreenViewModel
import com.juvinal.pay.ui.screens.authentication.RegistrationScreenViewModel
import com.juvinal.pay.ui.screens.inApp.InAppNavScreenViewModel
import com.juvinal.pay.ui.screens.inApp.dashboard.HomeScreenViewModel
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ChangePasswordScreenViewModel
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.PersonalDetailsScreenViewModel
import com.juvinal.pay.ui.screens.inApp.dashboard.profile.ProfileScreenViewModel
import com.juvinal.pay.ui.screens.inApp.transactions.deposit.DepositMoneyScreenViewModel
import com.juvinal.pay.ui.screens.inApp.transactions.loan.LoanScheduleScreenViewModel
import com.juvinal.pay.ui.screens.inApp.transactions.loan.RequestLoanScreenViewModel
import com.juvinal.pay.ui.screens.inApp.transactions.loan.UnpaidLoansScreenViewModel
import com.juvinal.pay.ui.screens.inApp.transactions.transactionsHistory.DepositHistoryScreenViewModel
import com.juvinal.pay.ui.screens.inApp.transactions.loan.LoanHistoryScreenViewModel
import com.juvinal.pay.ui.screens.inApp.transactions.loan.LoanRepaymentScreenViewModel

object AppViewModelFactory {
    @RequiresApi(Build.VERSION_CODES.O)
    val Factory = viewModelFactory {

        // initialize RegistrationScreenViewModel

        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            RegistrationScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize LoginScreenViewModel

        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            LoginScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                savedStateHandle = this.createSavedStateHandle(),
                dbRepository = juvinalPayApplication().container.dbRepository

            )
        }

        // initialize MembershipFeeScreenViewModel

        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            MembershipFeeScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize SplashScreenViewModel
        initializer {
            val dsRepository = juvinalPayApplication().dsRepository
            SplashScreenViewModel(
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize InAppNavScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            InAppNavScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                savedStateHandle = this.createSavedStateHandle(),
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize HomeScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            HomeScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize ProfileScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            ProfileScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize PersonalDetailsScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            PersonalDetailsScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize ChangePasswordScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            ChangePasswordScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize DepositMoneyScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            DepositMoneyScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize TransactionHistoryViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            DepositHistoryScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize RequestLoanScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            RequestLoanScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository,
            )
        }

        // initialize LoanHistoryScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            LoanHistoryScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize WelcomeScreenViewModel
        initializer {
            val dsRepository = juvinalPayApplication().dsRepository
            WelcomeScreenViewModel(
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize UnpaidLoansScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            UnpaidLoansScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize LoanScheduleScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            LoanScheduleScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                savedStateHandle = this.createSavedStateHandle(),
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }

        // initialize LoanRepaymentScreenViewModel
        initializer {
            val apiRepository = juvinalPayApplication().container.apiRepository
            val dsRepository = juvinalPayApplication().dsRepository
            LoanRepaymentScreenViewModel(
                apiRepository = apiRepository,
                dsRepository = dsRepository,
                savedStateHandle = this.createSavedStateHandle(),
                dbRepository = juvinalPayApplication().container.dbRepository
            )
        }
    }
}

fun CreationExtras.juvinalPayApplication(): JuvinalPay =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JuvinalPay)